package command.impl;

import command.Command;
import command.Commands;
import protocol.response.RespResponse;

public interface RedisCommand {

    Commands getType();

    RespResponse execute(Command command);

}