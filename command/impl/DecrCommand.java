package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class DecrCommand implements RedisCommand {

    private final MiniRedis redis;

    public DecrCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public String name() {
        return "DECR";
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 1) {
            return Resp.wrongArguments("DECR");
        }

        try {

            return Resp.integer(
                    redis.decr(
                            command.arguments().getFirst()
                    )
            );

        } catch (IllegalArgumentException e) {

            return Resp.error(
                    e.getMessage()
            );

        }

    }

}