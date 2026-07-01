package storage;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MiniRedis implements Serializable {

    private Map<String, String> store = new ConcurrentHashMap<>();

    private Map<String, Long> expiryMap = new ConcurrentHashMap<>();

    public MiniRedis() {
        loadSnapshot();
    }

    /*
     * ==========================================================
     * INTERNAL HELPERS
     * ==========================================================
     */

    private void cleanupExpiredKey(String key) {

        Long expireAt = expiryMap.get(key);

        if (expireAt == null) {
            return;
        }

        if (System.currentTimeMillis() >= expireAt) {

            store.remove(key);
            expiryMap.remove(key);

        }

    }

    private void cleanupAllExpiredKeys() {

        List<String> expiredKeys = new ArrayList<>();

        long now = System.currentTimeMillis();

        for (Map.Entry<String, Long> entry : expiryMap.entrySet()) {

            if (now >= entry.getValue()) {
                expiredKeys.add(entry.getKey());
            }

        }

        for (String key : expiredKeys) {

            store.remove(key);
            expiryMap.remove(key);

        }

    }

    private long parseNumber(String key) {

        cleanupExpiredKey(key);

        String value = store.get(key);

        if (value == null) {

            value = "0";

        }

        try {

            return Long.parseLong(value);

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(
                    "ERR value is not an integer or out of range");

        }

    }

    /*
     * ==========================================================
     * BASIC COMMANDS
     * ==========================================================
     */

    public void set(
            String key,
            String value) {

        store.put(
                key,
                value);

    }

    public String get(String key) {

        cleanupExpiredKey(key);

        return store.get(key);

    }

    public void del(String key) {

        store.remove(key);
        expiryMap.remove(key);

    }

    public void setExpiry(
            String key,
            Long expireAt) {

        if (!store.containsKey(key)) {
            return;
        }

        expiryMap.put(
                key,
                expireAt);

    }

    public void expire(
            String key,
            int seconds) {

        if (!store.containsKey(key)) {
            return;
        }

        expiryMap.put(
                key,
                System.currentTimeMillis()
                        + (seconds * 1000L));

    }

    /*
     * ==========================================================
     * REDIS STRING HELPERS
     * ==========================================================
     */

    public boolean exists(String key) {

        cleanupExpiredKey(key);

        return store.containsKey(key);

    }

    public long ttl(String key) {

        cleanupExpiredKey(key);

        if (!store.containsKey(key)) {
            return -2;
        }

        Long expireAt = expiryMap.get(key);

        if (expireAt == null) {
            return -1;
        }

        return Math.max(
                0,
                (expireAt - System.currentTimeMillis()) / 1000);

    }

    public long pttl(String key) {

        cleanupExpiredKey(key);

        if (!store.containsKey(key)) {
            return -2;
        }

        Long expireAt = expiryMap.get(key);

        if (expireAt == null) {
            return -1;
        }

        return Math.max(
                0,
                expireAt - System.currentTimeMillis());

    }

    public String type(String key) {

        cleanupExpiredKey(key);

        if (!store.containsKey(key)) {
            return "none";
        }

        return "string";

    }

    public long incr(String key) {

        long value = parseNumber(key);

        value++;

        store.put(
                key,
                Long.toString(value));

        return value;

    }

    public long decr(String key) {

        long value = parseNumber(key);

        value--;

        store.put(
                key,
                Long.toString(value));

        return value;

    }

    public long incrBy(
            String key,
            long amount) {

        long value = parseNumber(key);

        value += amount;

        store.put(
                key,
                Long.toString(value));

        return value;

    }

    public long decrBy(
            String key,
            long amount) {

        long value = parseNumber(key);

        value -= amount;

        store.put(
                key,
                Long.toString(value));

        return value;

    }

    public int append(
            String key,
            String value) {

        cleanupExpiredKey(key);

        String current = store.get(key);

        if (current == null) {
            current = "";
        }

        current += value;

        store.put(
                key,
                current);

        return current.length();

    }

    public int strlen(String key) {

        cleanupExpiredKey(key);

        String value = store.get(key);

        if (value == null) {
            return 0;
        }

        return value.length();

    }

    public void mset(
            Map<String, String> values) {

        for (Map.Entry<String, String> entry : values.entrySet()) {

            set(
                    entry.getKey(),
                    entry.getValue());

        }

    }

    public List<String> mget(
            List<String> keys) {

        List<String> values = new ArrayList<>();

        for (String key : keys) {

            values.add(
                    get(key));

        }

        return values;

    }
    /*
     * ==========================================================
     * FUTURE REDIS HELPERS
     * ==========================================================
     */

    public int dbSize() {

        cleanupAllExpiredKeys();

        return store.size();

    }

    public Set<String> keys() {

        cleanupAllExpiredKeys();

        return new HashSet<>(
                store.keySet());

    }

    public void flushDb() {

        store.clear();
        expiryMap.clear();

    }

    /*
     * ==========================================================
     * SNAPSHOT (SAVE)
     * ==========================================================
     */

    public void saveSnapshot() {

        cleanupAllExpiredKeys();

        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("dump.rdb"))) {

            out.writeObject(store);
            out.writeObject(expiryMap);

            System.out.println(
                    "(ok) Snapshot saved");

        } catch (Exception e) {

            System.out.println(
                    "(error) Failed to save snapshot: "
                            + e.getMessage());

        }

    }

    /*
     * ==========================================================
     * BACKGROUND SNAPSHOT (BGSAVE)
     * ==========================================================
     */

    public void bgSave() {

        cleanupAllExpiredKeys();

        Map<String, String> snapshotStore = new ConcurrentHashMap<>(store);

        Map<String, Long> snapshotExpiry = new ConcurrentHashMap<>(expiryMap);

        Thread.startVirtualThread(() -> {

            try (ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("dump.rdb"))) {

                out.writeObject(snapshotStore);
                out.writeObject(snapshotExpiry);

                System.out.println(
                        "(ok) Background snapshot completed");

            } catch (Exception e) {

                System.out.println(
                        "(error) Background snapshot failed: "
                                + e.getMessage());

            }

        });

        System.out.println(
                "(ok) Background saving started");

    }

    /*
     * ==========================================================
     * LOAD SNAPSHOT
     * ==========================================================
     */

    @SuppressWarnings("unchecked")
    public void loadSnapshot() {

        File file = new File("dump.rdb");

        if (!file.exists()) {

            System.out.println(
                    "(info) No snapshot found");

            return;

        }

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(file))) {

            store = (Map<String, String>) in.readObject();

            expiryMap = (Map<String, Long>) in.readObject();

            cleanupAllExpiredKeys();

            System.out.println(
                    "(ok) Snapshot loaded");

        } catch (Exception e) {

            System.out.println(
                    "(error) Failed to load snapshot: "
                            + e.getMessage());

        }

    }

}