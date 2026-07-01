package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class TypeCommand implements RedisCommand {

    private final MiniRedis redis;

    public TypeCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public String name() {
        return "TYPE";
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 1) {
            return Resp.wrongArguments("TYPE");
        }

        return Resp.simple(

                redis.type(
                        command.arguments().getFirst())

        );

    }

}