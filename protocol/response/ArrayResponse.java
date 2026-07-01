package protocol.response;

import java.util.List;

public record ArrayResponse(
        List<RespResponse> values
) implements RespResponse {
}