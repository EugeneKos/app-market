package ru.market.domain.validator.operation;

import ru.market.domain.data.Operation;
import ru.market.domain.exception.ValidateException;

public interface OperationValidator {
    void validateAmount(Operation operation) throws ValidateException;
}
