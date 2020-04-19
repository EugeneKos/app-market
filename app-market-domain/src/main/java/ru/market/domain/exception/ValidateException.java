package ru.market.domain.exception;

public class ValidateException extends RuntimeException {
    private static final long serialVersionUID = -7455515215373772945L;

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
