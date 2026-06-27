package server;

import java.util.List;

import command.Command;
import command.RespParser;

public class Tmp {
    public static void main(String[] args) {
        List<String> lines = List.of(

                "*3",

                "$3",

                "SET",

                "$4",

                "name",

                "$6",

                "Pratik"

        );
        RespParser parser = new RespParser();

        Command command = parser.parse(lines);
        System.out.println(command.type());
        System.out.println(command.arguments());
    }
}
