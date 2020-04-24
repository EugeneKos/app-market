package ru.market.domain.validator.operation;

import ru.market.domain.data.Operation;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.service.utils.ServiceUtils;
import ru.market.domain.validator.CommonValidator;

public class OperationValidator implements CommonValidator<Operation> {
    @Override
    public void validate(Operation operation) throws ValidateException {
        validateAmount(operation);
    }

    private void validateAmount(Operation operation) throws ValidateException {
        String amount = operation.getAmount();

        if(amount == null || amount.isEmpty()){
            throw new ValidateException("Сумма перевода должна быть заполнена.");
        }

        if(ServiceUtils.isMatchMoney(amount)){
            return;
        }

        throw new ValidateException("Сумма перевода заполнена некорректно");
    }
}
