package protocol.response;

public record ErrorResponse(
        String message
) implements RespResponse {
}