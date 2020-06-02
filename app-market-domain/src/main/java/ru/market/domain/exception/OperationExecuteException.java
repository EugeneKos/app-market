package ru.market.domain.exception;

public class OperationExecuteException extends RuntimeException {
    private static final long serialVersionUID = -1738604334841832446L;

    public OperationExecuteException(String message) {
        super(message);
    }
}
