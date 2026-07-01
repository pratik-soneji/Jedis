package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class StrLenCommand implements RedisCommand {

    private final MiniRedis redis;

    public StrLenCommand(
            MiniRedis redis
    ) {

        this.redis = redis;

    }

    @Override
    public String name() {

        return "STRLEN";

    }

    @Override
    public RespResponse execute(
            Command command
    ) {

        if (command.arguments().size() != 1) {

            return Resp.wrongArguments(
                    "STRLEN"
            );

        }

        return Resp.integer(

                redis.strlen(

                        command.arguments().getFirst()

                )

        );

    }

}