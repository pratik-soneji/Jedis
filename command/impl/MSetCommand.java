package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

import java.util.HashMap;
import java.util.Map;

public class MSetCommand implements RedisCommand {

    private final MiniRedis redis;

    public MSetCommand(
            MiniRedis redis
    ) {

        this.redis = redis;

    }

    @Override
    public String name() {

        return "MSET";

    }

    @Override
    public RespResponse execute(
            Command command
    ) {

        if (command.arguments().isEmpty()
                || command.arguments().size() % 2 != 0) {

            return Resp.wrongArguments(
                    "MSET"
            );

        }

        Map<String, String> values =
                new HashMap<>();

        for (int i = 0;
             i < command.arguments().size();
             i += 2) {

            values.put(

                    command.arguments().get(i),

                    command.arguments().get(i + 1)

            );

        }

        redis.mset(values);

        return Resp.ok();

    }

}