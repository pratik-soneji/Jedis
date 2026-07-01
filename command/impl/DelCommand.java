package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class DelCommand implements RedisCommand {

    private final MiniRedis redis;

    public DelCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public String name() {
        return "DEL";
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 1) {
            return Resp.wrongArguments("DEL");
        }

        redis.del(
                command.arguments().getFirst()
        );

        return Resp.ok();

    }

}