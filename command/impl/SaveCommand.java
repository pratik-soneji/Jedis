package command.impl;

import command.Command;
import command.Commands;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class SaveCommand implements RedisCommand {

    private final MiniRedis redis;

    public SaveCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public Commands getType() {
        return Commands.SAVE;
    }

    @Override
    public RespResponse execute(Command command) {

        redis.saveSnapshot();

        return Resp.ok();

    }

}