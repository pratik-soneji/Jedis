package command.impl;

import command.Command;
import protocol.Resp;
import protocol.response.RespResponse;

public class QuitCommand implements RedisCommand {

    @Override
    public String name() {
        return "QUIT";
    }

    @Override
    public RespResponse execute(Command command) {

        if (!command.arguments().isEmpty()) {
            return Resp.wrongArguments("QUIT");
        }

        return Resp.ok();

    }

}