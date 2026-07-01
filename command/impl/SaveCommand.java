package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class SaveCommand implements RedisCommand {

    private final MiniRedis redis;

    public SaveCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public String name() {
        return "SAVE";
    }

    @Override
    public RespResponse execute(Command command) {

        if (!command.arguments().isEmpty()) {
            return Resp.wrongArguments("SAVE");
        }

        redis.saveSnapshot();

        return Resp.ok();

    }

}