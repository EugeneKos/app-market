package ru.market.domain.validator.operation;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.service.utils.ServiceUtils;
import ru.market.domain.validator.CommonValidator;

import java.time.LocalDate;
import java.time.LocalTime;

public class OperationValidator implements CommonValidator<Operation> {
    private BankAccount bankAccount;

    public OperationValidator(BankAccount bankAccount){
        this.bankAccount = bankAccount;
    }

    @Override
    public void validate(Operation operation) throws ValidateException {
        validateAmount(operation);
        validateDate(operation, bankAccount.getDateCreated());
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
