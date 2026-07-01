package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

public class BgSaveCommand implements RedisCommand {

    private final MiniRedis redis;

    public BgSaveCommand(MiniRedis redis) {
        this.redis = redis;
    }

    @Override
    public String name() {
        return "BGSAVE";
    }

    @Override
    public RespResponse execute(Command command) {

        if (!command.arguments().isEmpty()) {
            return Resp.wrongArguments("BGSAVE");
        }

        redis.bgSave();

        return Resp.ok();

    }

}