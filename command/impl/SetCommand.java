package command.impl;

import command.Command;
import command.Commands;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class SetCommand implements RedisCommand {

    private final MiniRedis redis;

    public SetCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public Commands getType() {
        return Commands.SET;
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 2) {
            return Resp.error(
                    "wrong number of arguments for 'SET'"
            );
        }

        String key = command.arguments().get(0);
        String value = command.arguments().get(1);

        redis.set(key, value);

        return Resp.ok();
    }

}