package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class ExpireCommand implements RedisCommand {

    private final MiniRedis redis;

    public ExpireCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public String name() {
        return "EXPIRES";
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 2) {
            return Resp.wrongArguments("EXPIRES");
        }

        String key = command.arguments().get(0);

        int seconds;

        try {

            seconds = Integer.parseInt(
                    command.arguments().get(1)
            );

        } catch (NumberFormatException e) {

            return Resp.error(
                    "ERR value is not an integer or out of range"
            );

        }

        redis.expire(
                key,
                seconds
        );

        return Resp.ok();

    }

}