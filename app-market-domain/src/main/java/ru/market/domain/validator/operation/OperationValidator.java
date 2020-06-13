package ru.market.domain.validator.operation;

import ru.market.domain.data.MoneyAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.validator.utils.ValidatorUtils;
import ru.market.domain.validator.CommonValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class OperationValidator implements CommonValidator<Operation> {
    private MoneyAccount moneyAccount;

    public OperationValidator(MoneyAccount moneyAccount){
        this.moneyAccount = moneyAccount;
    }

    @Override
    public void validate(Operation operation) throws ValidateException {
        validateAmount(operation);
        validateDate(operation, moneyAccount.getDateCreated());
    }

    private void validateAmount(Operation operation) throws ValidateException {
        BigDecimal amount = operation.getAmount();

        if(amount == null){
            throw new ValidateException("Сумма перевода должна быть заполнена.");
        }

        if(ValidatorUtils.isMatchMoney(amount.toString())){
            return;
        }

        throw new ValidateException("Сумма перевода заполнена некорректно");
    }

    private void validateDate(Operation operation, LocalDate bankAccountDateCreated){
        LocalDate operationDate = operation.getDate();
        LocalTime operationTime = operation.getTime();

        if(operationDate.isBefore(bankAccountDateCreated)){
            throw new ValidateException("Дата операции не может быть раньше чем был создан банковский счет");
        }
        if(LocalDate.now().isBefore(operationDate)){
            throw new ValidateException("Дата операции не может быть позднее сегодняшнего дня");
        }
        if(LocalTime.now().isBefore(operationTime)){
            throw new ValidateException("Время операции не может быть позднее текущего времени");
        }
    }
}
