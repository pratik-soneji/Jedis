package client;

import protocol.RespOutputWriter;
import protocol.response.BulkStringResponse;
import protocol.response.ErrorResponse;
import protocol.response.IntegerResponse;
import protocol.response.NullBulkStringResponse;
import protocol.response.RespResponse;
import protocol.response.SimpleStringResponse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try (

                Socket socket =
                        new Socket("localhost", 6379);

                BufferedInputStream input =
                        new BufferedInputStream(
                                socket.getInputStream()
                        );

                BufferedOutputStream output =
                        new BufferedOutputStream(
                                socket.getOutputStream()
                        );

                Scanner scanner =
                        new Scanner(System.in)

        ) {

            while (true) {

                System.out.print("> ");

                String line = scanner.nextLine();

                if (line.equalsIgnoreCase("exit")) {
                    break;
                }

                String[] parts = line.split(" ");

                sendCommand(output, parts);

                System.out.println(readResponse(input));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private static void sendCommand(
            BufferedOutputStream output,
            String... args
    ) throws IOException {

        output.write(
                ("*" + args.length + "\r\n")
                        .getBytes(StandardCharsets.UTF_8)
        );

        for (String arg : args) {

            byte[] bytes =
                    arg.getBytes(StandardCharsets.UTF_8);

            output.write(
                    ("$" + bytes.length + "\r\n")
                            .getBytes(StandardCharsets.UTF_8)
            );

            output.write(bytes);

            output.write(
                    "\r\n".getBytes(StandardCharsets.UTF_8)
            );

        }

        output.flush();

    }

    private static String readResponse(
            BufferedInputStream input
    ) throws IOException {

        int first = input.read();

        if (first == -1) {
            return "Server closed connection.";
        }

        switch ((char) first) {

            case '+':

                return readLine(input);

            case '-':

                return "ERROR: " + readLine(input);

            case ':':

                return readLine(input);

            case '$':

                int length =
                        Integer.parseInt(
                                readLine(input)
                        );

                if (length == -1) {
                    return "(nil)";
                }

                byte[] bytes =
                        input.readNBytes(length);

                input.read();
                input.read();

                return new String(
                        bytes,
                        StandardCharsets.UTF_8
                );

            default:

                return "Unknown RESP response.";

        }

    }

    private static String readLine(
            BufferedInputStream input
    ) throws IOException {

        StringBuilder builder =
                new StringBuilder();

        while (true) {

            int b = input.read();

            if (b == '\r') {

                input.read();

                break;

            }

            builder.append((char) b);

        }

        return builder.toString();

    }

}