package protocol.response;

public sealed interface RespResponse
        permits SimpleStringResponse,
                BulkStringResponse,
                IntegerResponse,
                ErrorResponse,
                NullBulkStringResponse,
                 ArrayResponse {
}

// Why sealed?

// Answer:

// RESP has a fixed set of response types. A sealed interface models that closed hierarchy directly, preventing invalid implementations and enabling exhaustive pattern matching.