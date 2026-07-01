package command.impl;

import command.Command;
import protocol.response.RespResponse;

public interface RedisCommand {

    String name();

    RespResponse execute(Command command);

}