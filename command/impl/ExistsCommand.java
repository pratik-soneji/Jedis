package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class ExistsCommand implements RedisCommand {

    private final MiniRedis redis;

    public ExistsCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public String name() {
        return "EXISTS";
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 1) {
            return Resp.wrongArguments("EXISTS");
        }

        boolean exists =
                redis.exists(
                        command.arguments().getFirst()
                );

        return Resp.integer(
                exists ? 1 : 0
        );

    }

}