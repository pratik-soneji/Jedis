package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class TTLCommand implements RedisCommand {

    private final MiniRedis redis;

    public TTLCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public String name() {
        return "TTL";
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 1) {
            return Resp.wrongArguments("TTL");
        }

        return Resp.integer(

                redis.ttl(
                        command.arguments().getFirst())

        );

    }

}