package command.impl;

import command.Command;
import command.Commands;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class GetCommand implements RedisCommand {

    private final MiniRedis redis;

    public GetCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public Commands getType() {
        return Commands.GET;
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 1) {
            return Resp.error(
                    "wrong number of arguments for 'GET'"
            );
        }

        String key = command.arguments().get(0);

        String value = redis.get(key);

        return Resp.bulk(value);

    }

}