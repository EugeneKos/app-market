package ru.market.client.exception;

public class HttpConnectionException extends Exception {
    private static final long serialVersionUID = -8364634315260291408L;

    public HttpConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
