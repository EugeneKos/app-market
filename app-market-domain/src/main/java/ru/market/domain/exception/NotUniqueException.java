package ru.market.domain.exception;

public class NotUniqueException extends RuntimeException {
    private static final long serialVersionUID = 4747567158499439216L;

    public NotUniqueException(String message) {
        super(message);
    }
}
