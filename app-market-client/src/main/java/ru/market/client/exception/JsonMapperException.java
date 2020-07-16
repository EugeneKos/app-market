package ru.market.client.exception;

public class JsonMapperException extends RuntimeException {
    private static final long serialVersionUID = 737045652690972923L;

    public JsonMapperException(String message, Throwable cause) {
        super(message, cause);
    }
}
