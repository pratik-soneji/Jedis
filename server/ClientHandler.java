package server;

import command.Command;
import command.CommandExecutor;
import protocol.RespInputParser;
import protocol.RespOutputWriter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {

        private final Socket socket;

        private final RespInputParser parser;

        private final CommandExecutor executor;

        public ClientHandler(
                        Socket socket,
                        CommandExecutor executor) {

                this.socket = socket;
                this.executor = executor;
                this.parser = new RespInputParser();

        }

        @Override
        public void run() {

                try (

                                BufferedInputStream input = new BufferedInputStream(
                                                socket.getInputStream());

                                BufferedOutputStream output = new BufferedOutputStream(
                                                socket.getOutputStream());

                ) {

                        RespOutputWriter writer = new RespOutputWriter(output);

                        while (true) {

                                Command command = parser.parse(input);

                                writer.write(
                                                executor.execute(command));

                        }

                }

                catch (Exception e) {
                        e.printStackTrace();
                }

                finally {

                        try {

                                socket.close();

                        }

                        catch (IOException ignored) {

                        }

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