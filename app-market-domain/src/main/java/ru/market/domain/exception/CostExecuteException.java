package ru.market.domain.exception;

public class CostExecuteException extends RuntimeException {
    private static final long serialVersionUID = -3352366668945009533L;

    public CostExecuteException(String message) {
        super(message);
    }
}
