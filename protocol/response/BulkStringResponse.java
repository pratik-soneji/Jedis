package protocol.response;

public record BulkStringResponse(
        String value
) implements RespResponse {
}