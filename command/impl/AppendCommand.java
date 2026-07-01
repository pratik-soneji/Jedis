package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class AppendCommand implements RedisCommand {

    private final MiniRedis redis;

    public AppendCommand(
            MiniRedis redis
    ) {

        this.redis = redis;

    }

    @Override
    public String name() {

        return "APPEND";

    }

    @Override
    public RespResponse execute(
            Command command
    ) {

        if (command.arguments().size() != 2) {

            return Resp.wrongArguments(
                    "APPEND"
            );

        }

        return Resp.integer(

                redis.append(

                        command.arguments().getFirst(),

                        command.arguments().get(1)

                )

        );

    }

}