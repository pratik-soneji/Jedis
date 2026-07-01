package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class PTTLCommand implements RedisCommand {

    private final MiniRedis redis;

    public PTTLCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public String name() {
        return "PTTL";
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 1) {
            return Resp.wrongArguments("PTTL");
        }

        return Resp.integer(

                redis.pttl(
                        command.arguments().getFirst()
                )

        );

    }

}