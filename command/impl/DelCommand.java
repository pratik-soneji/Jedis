package command.impl;

import command.Command;
import command.Commands;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class DelCommand implements RedisCommand {

    private final MiniRedis redis;

    public DelCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public Commands getType() {
        return Commands.DEL;
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 1) {
            return Resp.error(
                    "wrong number of arguments for 'DEL'"
            );
        }

        String key = command.arguments().get(0);

        redis.del(key);

        return Resp.ok();

    }

}