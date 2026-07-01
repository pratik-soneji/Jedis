package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class IncrCommand implements RedisCommand {

    private final MiniRedis redis;

    public IncrCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public String name() {
        return "INCR";
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 1) {
            return Resp.wrongArguments("INCR");
        }

        try {

            return Resp.integer(
                    redis.incr(
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