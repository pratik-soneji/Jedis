package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;

public class PingCommand implements RedisCommand {

    @Override
    public String name() {
        return "PING";
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() > 1) {
            return Resp.wrongArguments("PING");
        }

        if (command.arguments().isEmpty()) {
            return Resp.pong();
        }

        return Resp.bulk(
                command.arguments().getFirst()
        );

    }

}