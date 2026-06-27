package command.impl;

import command.Command;
import command.Commands;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class BgSaveCommand implements RedisCommand {

    private final MiniRedis redis;

    public BgSaveCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public Commands getType() {
        return Commands.BGSAVE;
    }

    @Override
    public RespResponse execute(Command command) {

        redis.bgSave();

        return Resp.ok();

    }

}