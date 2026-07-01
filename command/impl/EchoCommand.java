package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;

public class EchoCommand implements RedisCommand {

    @Override
    public String name() {
        return "ECHO";
    }

    @Override
    public RespResponse execute(Command command) {

        if (command.arguments().size() != 1) {
            return Resp.wrongArguments("ECHO");
        }

        return Resp.bulk(
                command.arguments().getFirst()
        );

    }

}