package protocol;

import protocol.response.*;

import java.util.List;

public final class Resp {

    private Resp() {
    }

    private static final SimpleStringResponse OK = new SimpleStringResponse("OK");

    private static final SimpleStringResponse PONG = new SimpleStringResponse("PONG");

    private static final NullBulkStringResponse NULL = new NullBulkStringResponse();

    public static RespResponse ok() {
        return OK;
    }

    public static RespResponse pong() {
        return PONG;
    }

    public static RespResponse bulk(
            String value) {

        if (value == null) {
            return NULL;
        }

        return new BulkStringResponse(value);

    }

    public static RespResponse simple(
            String value) {

        return new SimpleStringResponse(value);

    }

    public static RespResponse integer(
            long value) {

        return new IntegerResponse(value);

    }

    public static RespResponse error(
            String message) {

        return new ErrorResponse(message);

    }

    public static RespResponse nullBulk() {

        return NULL;

    }

    public static RespResponse array(
            List<RespResponse> values) {

        return new ArrayResponse(values);

    }

    

    public static RespResponse wrongArguments(
            String command) {

        return error(
                "ERR wrong number of arguments for '"
                        + command.toLowerCase()
                        + "' command");

    }
}