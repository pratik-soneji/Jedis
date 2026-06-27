package server;

import command.CommandExecutor;
import storage.MiniRedis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args)
            throws IOException {

        MiniRedis redis =
                new MiniRedis();

        CommandExecutor executor =
                new CommandExecutor(redis);

        try (

                ServerSocket server =
                        new ServerSocket(6379)

        ) {

            System.out.println(
                    "MiniRedis started on port 6379..."
            );

            while (true) {

                Socket socket =
                        server.accept();

                System.out.println(
                        "Client connected : "
                                + socket.getInetAddress()
                );

                Thread.startVirtualThread(

                        new ClientHandler(
                                socket,
                                executor
                        )       

                );

            }

        }

    }

}