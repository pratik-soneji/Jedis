package storage;
import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MiniRedis implements Serializable {

    private Map<String, String> store =
            new ConcurrentHashMap<>();

    private Map<String, Long> expiryMap =
            new ConcurrentHashMap<>();

    public MiniRedis() {
        loadSnapshot();
    }

    public void set(String key, String value) {
        store.put(key, value);
    }

    public String get(String key) {

        if (expiryMap.containsKey(key)) {

            long expireAt = expiryMap.get(key);

            if (System.currentTimeMillis() > expireAt) {

                store.remove(key);
                expiryMap.remove(key);

                return null;
            }
        }

        return store.get(key);
    }

    public void del(String key) {
        store.remove(key);
        expiryMap.remove(key);
    }

    public void setExpiry(String key, Long expireAt) {
        expiryMap.put(key, expireAt);
    }

    public void saveSnapshot() {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream("dump.rdb"))) {

            out.writeObject(store);
            out.writeObject(expiryMap);

            System.out.println("(ok) Snapshot saved");

        } catch (Exception e) {

            System.out.println(
                    "(error) Failed to save snapshot: "
                            + e.getMessage()
            );
        }
    }

    public void bgSave() {

        Map<String, String> snapshotStore =
                new ConcurrentHashMap<>(store);

        Map<String, Long> snapshotExpiry =
                new ConcurrentHashMap<>(expiryMap);

        Thread.startVirtualThread(() -> {

            try (ObjectOutputStream out =
                         new ObjectOutputStream(
                                 new FileOutputStream("dump.rdb"))) {

                out.writeObject(snapshotStore);
                out.writeObject(snapshotExpiry);

                System.out.println(
                        "(ok) Background snapshot completed"
                );

            } catch (Exception e) {

                System.out.println(
                        "(error) Background snapshot failed: "
                                + e.getMessage()
                );
            }

        });

        System.out.println(
                "(ok) Background saving started"
        );
    }

    @SuppressWarnings("unchecked")
    public void loadSnapshot() {

        File file = new File("dump.rdb");

        if (!file.exists()) {

            System.out.println(
                    "(info) No snapshot found"
            );

            return;
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream(file))) {

            store =
                    (Map<String, String>)
                            in.readObject();

            expiryMap =
                    (Map<String, Long>)
                            in.readObject();

            System.out.println(
                    "(ok) Snapshot loaded"
            );

        } catch (Exception e) {

            System.out.println(
                    "(error) Failed to load snapshot: "
                            + e.getMessage()
            );
        }
    }
}