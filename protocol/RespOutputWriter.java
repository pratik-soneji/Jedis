package protocol;

import protocol.response.*;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RespOutputWriter {

    private final BufferedOutputStream output;

    public RespOutputWriter(
            BufferedOutputStream output
    ) {
        this.output = output;
    }

    public void write(
            RespResponse response
    ) throws IOException {

        switch (response) {

            case SimpleStringResponse simple ->

                    writeSimpleString(
                            simple.value()
                    );

            case BulkStringResponse bulk ->

                    writeBulkString(
                            bulk.value()
                    );

            case IntegerResponse integer ->

                    writeInteger(
                            integer.value()
                    );

            case ErrorResponse error ->

                    writeError(
                            error.message()
                    );

            case NullBulkStringResponse ignored ->

                    writeNullBulkString();

        }

        output.flush();

    }

    private void writeSimpleString(
            String value
    ) throws IOException {

        output.write(
                ("+" + value + "\r\n")
                        .getBytes(StandardCharsets.UTF_8)
        );

    }

    private void writeError(
            String error
    ) throws IOException {

        output.write(
                ("-" + error + "\r\n")
                        .getBytes(StandardCharsets.UTF_8)
        );

    }

    private void writeInteger(
            long value
    ) throws IOException {

        output.write(
                (":" + value + "\r\n")
                        .getBytes(StandardCharsets.UTF_8)
        );

    }

    private void writeBulkString(
            String value
    ) throws IOException {

        byte[] bytes =
                value.getBytes(
                        StandardCharsets.UTF_8
                );

        output.write(
                ("$" + bytes.length + "\r\n")
                        .getBytes(StandardCharsets.UTF_8)
        );

        output.write(bytes);

        output.write(
                "\r\n".getBytes(
                        StandardCharsets.UTF_8
                )
        );

    }

    private void writeNullBulkString()
            throws IOException {

        output.write(
                "$-1\r\n"
                        .getBytes(StandardCharsets.UTF_8)
        );

    }

}