package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class SetCommand implements RedisCommand {

    private final MiniRedis redis;

    public SetCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public String name() {
        return "SET";
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 2) {
            return Resp.wrongArguments("SET");
        }

        redis.set(
                command.arguments().get(0),
                command.arguments().get(1)
        );

        return Resp.ok();

    }

}