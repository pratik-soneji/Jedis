package command.impl;

import command.Command;
import command.Commands;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class ExpireCommand implements RedisCommand {

    private final MiniRedis redis;

    public ExpireCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public Commands getType() {
        return Commands.EXPIRES;
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 2) {
            return Resp.error(
                    "wrong number of arguments for 'EXPIRES'"
            );
        }

        String key = command.arguments().get(0);

        long seconds;

        try {

            seconds = Long.parseLong(
                    command.arguments().get(1)
            );

        } catch (NumberFormatException e) {

            return Resp.error(
                    "value is not an integer"
            );

        }

        long expireAt =
                System.currentTimeMillis()
                        + (seconds * 1000);

        redis.setExpiry(
                key,
                expireAt
        );

        return Resp.ok();

    }

}