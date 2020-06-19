package ru.market.client.exception;

public class RestClientException extends RuntimeException {
    private static final long serialVersionUID = 6681322945113396035L;

    public RestClientException(String message) {
        super(message);
    }

    public RestClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
