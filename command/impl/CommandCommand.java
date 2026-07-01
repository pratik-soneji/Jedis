package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;

import java.util.List;

public class CommandCommand implements RedisCommand {

    @Override
    public String name() {
        return "COMMAND";
    }

    @Override
    public RespResponse execute(Command command) {

        if (!command.arguments().isEmpty()) {
            return Resp.wrongArguments("COMMAND");
        }

        return Resp.array(
                List.of()
        );

    }

}