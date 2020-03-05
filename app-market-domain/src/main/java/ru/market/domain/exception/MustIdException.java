package ru.market.domain.exception;

public class MustIdException extends RuntimeException {
    private static final long serialVersionUID = -7858637194511089381L;

    public MustIdException(String message) {
        super(message);
    }
}
