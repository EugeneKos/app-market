package ru.market.domain.exception;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1523939503036021204L;

    public NotFoundException(String message) {
        super(message);
    }
}
