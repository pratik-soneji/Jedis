package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class IncrByCommand implements RedisCommand {

    private final MiniRedis redis;

    public IncrByCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public String name() {
        return "INCRBY";
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 2) {
            return Resp.wrongArguments("INCRBY");
        }

        long amount;

        try {

            amount = Long.parseLong(
                    command.arguments().get(1)
            );

        } catch (NumberFormatException e) {

            return Resp.error(
                    "ERR value is not an integer or out of range"
            );

        }

        try {

            return Resp.integer(
                    redis.incrBy(
                            command.arguments().getFirst(),
                            amount
                    )
            );

        } catch (IllegalArgumentException e) {

            return Resp.error(
                    e.getMessage()
            );

        }

    }

}