package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

import java.util.List;

public class MGetCommand implements RedisCommand {

    private final MiniRedis redis;

    public MGetCommand(
            MiniRedis redis) {

        this.redis = redis;

    }

    @Override
    public String name() {

        return "MGET";

    }

    @Override
    public RespResponse execute(
            Command command) {

        if (command.arguments().isEmpty()) {

            return Resp.wrongArguments(
                    "MGET");

        }

        List<String> values = redis.mget(
                command.arguments());

        List<RespResponse> responses = values.stream()
                .map(Resp::bulk)
                .toList();

        return Resp.array(responses);

    }

}