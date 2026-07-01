package command;

import command.impl.*;
import protocol.Resp;
import protocol.response.RespResponse;
import storage.MiniRedis;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {

    private final Map<String, RedisCommand> commandMap;

    public CommandExecutor(MiniRedis redis) {

        commandMap = new HashMap<>();

        register(new SetCommand(redis));
        register(new GetCommand(redis));
        register(new DelCommand(redis));

        register(new ExistsCommand(redis));
        register(new TTLCommand(redis));
        register(new PTTLCommand(redis));
        register(new TypeCommand(redis));

        register(new IncrCommand(redis));
        register(new DecrCommand(redis));
        register(new IncrByCommand(redis));
        register(new DecrByCommand(redis));

        register(new SaveCommand(redis));
        register(new BgSaveCommand(redis));

        register(new ExpireCommand(redis));

        register(new PingCommand());
        register(new EchoCommand());
        register(new QuitCommand());
        register(new CommandCommand());

        register(new AppendCommand(redis));
        register(new StrLenCommand(redis));
        register(new MSetCommand(redis));
        register(new MGetCommand(redis));

    }

    private void register(RedisCommand command) {

        commandMap.put(
                command.name().toUpperCase(),
                command);

    }

    public RespResponse execute(Command command) {

        RedisCommand redisCommand = commandMap.get(
                command.name().toUpperCase());

        if (redisCommand == null) {

            return Resp.error(
                    "ERR unknown command '"
                            + command.name().toLowerCase()
                            + "'");

        }

        return redisCommand.execute(command);

    }

}