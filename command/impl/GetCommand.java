package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class GetCommand implements RedisCommand {

    private final MiniRedis redis;

    public GetCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public String name() {
        return "GET";
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 1) {
            return Resp.wrongArguments("GET");
        }

        return Resp.bulk(
                redis.get(
                        command.arguments().getFirst()
                )
        );

    }

}