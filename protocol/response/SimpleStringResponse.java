package protocol.response;

public record SimpleStringResponse(
        String value
) implements RespResponse {
}

// Why records?

// Answer:

// These classes are immutable data carriers. Records automatically generate constructors, accessors, equals(), hashCode(), and toString(), reducing boilerplate while clearly expressing intent.