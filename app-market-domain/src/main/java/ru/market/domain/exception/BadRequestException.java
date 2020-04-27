package ru.market.domain.exception;

public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 3091414479263038292L;

    public BadRequestException(String message) {
        super(message);
    }
}
