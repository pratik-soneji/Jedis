package command;

import command.impl.*;
import protocol.response.RespResponse;
import storage.MiniRedis;

import java.util.EnumMap;
import java.util.Map;

public class CommandExecutor {

    private final Map<Commands, RedisCommand> commandMap;

    public CommandExecutor(MiniRedis redis) {

        commandMap = new EnumMap<>(Commands.class);

        register(new SetCommand(redis));
        register(new GetCommand(redis));
        register(new DelCommand(redis));
        register(new SaveCommand(redis));
        register(new BgSaveCommand(redis));
        register(new ExpireCommand(redis));

    }

    private void register(
            RedisCommand command
    ) {

        commandMap.put(
                command.getType(),
                command
        );

    }

    public RespResponse execute(
            Command command
    ) {

        RedisCommand redisCommand =
                commandMap.get(
                        command.type()
                );

        if (redisCommand == null) {

            return protocol.Resp.error(
                    "Unknown command"
            );

        }

        return redisCommand.execute(
                command
        );

    }

}