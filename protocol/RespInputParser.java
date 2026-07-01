package protocol;

import command.Command;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RespInputParser {

        public Command parse(
                        BufferedInputStream input) throws IOException {

                String header = readLine(input);
                System.out.println("================================");
                System.out.println("RAW RESP PACKET");

                System.out.println(header);

                if (header == null || !header.startsWith("*")) {
                        throw new IOException(
                                        "Invalid RESP Array");
                }

                int totalElements = Integer.parseInt(
                                header.substring(1));

                if (totalElements < 1) {
                        throw new IOException(
                                        "Empty RESP Command");
                }

                String commandName = readBulkString(input);

                List<String> arguments = new ArrayList<>();

                for (int i = 1; i < totalElements; i++) {

                        arguments.add(
                                        readBulkString(input));

                }

                return new Command(
                                commandName,
                                arguments);

        }

        private String readBulkString(
                        BufferedInputStream input) throws IOException {

                String bulkHeader = readLine(input);

                System.out.println(bulkHeader);

                if (bulkHeader == null ||
                                !bulkHeader.startsWith("$")) {

                        throw new IOException(
                                        "Invalid Bulk String");
                }

                int length = Integer.parseInt(
                                bulkHeader.substring(1));

                byte[] bytes = input.readNBytes(length);

                // Consume exactly ONE CRLF
                if (input.read() != '\r' || input.read() != '\n') {
                        throw new IOException(
                                        "Expected CRLF after bulk string");
                }

                String value = new String(
                                bytes,
                                StandardCharsets.UTF_8);

                System.out.println(value);

                return value;
        }

        private String readLine(
                        BufferedInputStream input) throws IOException {

                StringBuilder builder = new StringBuilder();

                while (true) {

                        int b = input.read();

                        if (b == -1) {

                                return null;

                        }

                        if (b == '\r') {

                                input.read();

                                break;

                        }

                        builder.append(
                                        (char) b);

                }

                return builder.toString();

        }

}