package protocol.response;

public record IntegerResponse(
        long value
) implements RespResponse {
}