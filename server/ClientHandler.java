package server;

import command.Command;
import command.CommandExecutor;
import protocol.RespInputParser;
import protocol.RespOutputWriter;
import protocol.response.RespResponse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

        private final Socket socket;

        private final CommandExecutor executor;

        public ClientHandler(
                        Socket socket,
                        CommandExecutor executor) {

                this.socket = socket;
                this.executor = executor;

        }

        @Override
        public void run() {

                try (

                                socket;

                                BufferedInputStream input = new BufferedInputStream(
                                                socket.getInputStream());

                                BufferedOutputStream output = new BufferedOutputStream(
                                                socket.getOutputStream())

                ) {

                        RespInputParser parser = new RespInputParser();

                        RespOutputWriter writer = new RespOutputWriter(output);

                        while (true) {

                                Command command = parser.parse(input);

                                if (command == null) {
                                        break;
                                }

                                RespResponse response = executor.execute(command);

                                writer.write(response);

                                if (command.name().equalsIgnoreCase("QUIT")) {
                                        break;
                                }

                        }

                } catch (Exception e) {

                        e.printStackTrace();

                }

        }

}

// VIsual
// TCP

// ↓

// RespInputParser

// ↓

// Command

// ↓

// Executor

// ↓

// RespResponse

// ↓

// RespOutputWriter

// ↓

// TCP