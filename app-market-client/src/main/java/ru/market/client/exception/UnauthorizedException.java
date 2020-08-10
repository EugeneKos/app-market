package ru.market.client.exception;

public class UnauthorizedException extends RestClientException {
    private static final long serialVersionUID = 6809303023680966766L;

    public UnauthorizedException(String message) {
        super(message);
    }
}
