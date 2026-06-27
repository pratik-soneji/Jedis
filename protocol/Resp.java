package protocol;

import protocol.response.*;

public final class Resp {

    private Resp() {}

    private static final SimpleStringResponse OK =
            new SimpleStringResponse("OK");

    public static RespResponse ok() {
        return OK;
    }

    public static RespResponse bulk(String value) {

        if (value == null) {
            return new NullBulkStringResponse();
        }

        return new BulkStringResponse(value);
    }

    public static RespResponse error(String message) {
        return new ErrorResponse(message);
    }

    public static RespResponse integer(long value) {
        return new IntegerResponse(value);
    }

    public static RespResponse simple(String value) {
        return new SimpleStringResponse(value);
    }

    public static RespResponse nullBulk() {
        return new NullBulkStringResponse();
    }

}


// Why private Resp(){}?

// You should never do

// new Resp();

// Makes no sense.

// Everything is

// Resp.ok();

// So we block object creation.

// This is called a Utility Class.

// Java itself has many:

// Math.max()

// Collections.sort()

// Arrays.asList()

// Objects.requireNonNull()

// Have you ever done

// new Math()

// ?

// No.

// Exactly.