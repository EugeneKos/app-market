package ru.market.domain.validator.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.domain.data.MoneyAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.validator.utils.ValidatorUtils;
import ru.market.domain.validator.CommonValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OperationValidator implements CommonValidator<Operation> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationValidator.class);

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
        LOGGER.info("Валидация суммы операции");
        BigDecimal amount = operation.getAmount();

        if(amount == null){
            throw new ValidateException("Сумма операции должна быть заполнена.");
        }
        LOGGER.debug("Operation [validate amount] amount = {}", amount);

        if(ValidatorUtils.isMatchMoney(amount.toString())){
            LOGGER.info("Валидация суммы операции прошла успешно");
            return;
        }

        throw new ValidateException("Сумма операции заполнена некорректно");
    }

    private void validateDate(Operation operation, LocalDate bankAccountDateCreated){
        LOGGER.info("Валидация даты операции");
        LocalDate operationDate = operation.getDate();
        LocalTime operationTime = operation.getTime();
        LOGGER.debug("Operation [validate date] bankAccountDateCreated = {}, operationDate = {}, operationTime = {}",
                bankAccountDateCreated, operationDate, operationTime
        );

        if(operationDate.isBefore(bankAccountDateCreated)){
            throw new ValidateException("Дата операции не может быть раньше чем был создан банковский счет");
        }
        if(LocalDateTime.now().isBefore(LocalDateTime.of(operationDate, operationTime))){
            throw new ValidateException("Дата и время операции не может быть позднее текущей даты и времени");
        }
        LOGGER.info("Валидация даты операции прошла успешно");
    }
}
