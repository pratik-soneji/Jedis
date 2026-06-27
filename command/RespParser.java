package command;

import java.util.ArrayList;
import java.util.List;

public class RespParser {

    public Command parse(List<String> lines) {

        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("Empty RESP input");
        }

        String header = lines.get(0);

        if (!header.startsWith("*")) {
            throw new IllegalArgumentException("Invalid RESP Array");
        }

        int totalElements =
                Integer.parseInt(
                        header.substring(1)
                );

        if (totalElements < 1) {
            throw new IllegalArgumentException("Invalid RESP");
        }

        Commands type =
                Commands.valueOf(
                        lines.get(2).toUpperCase()
                );

        List<String> arguments =
                new ArrayList<>();

        for (int i = 4; i < lines.size(); i += 2) {

            arguments.add(
                    lines.get(i)
            );

        }

        return new Command(
                type,
                arguments
        );

    }

}