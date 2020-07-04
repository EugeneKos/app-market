package ru.market.domain.validator.operation;

import org.junit.Test;

import ru.market.domain.data.MoneyAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.exception.ValidateException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class OperationValidatorTest {
    private MoneyAccount moneyAccount;

    @Test
    public void validateTest(){
        Operation operation = createOperationAndInitMoneyAccount(
                "256.45", LocalDate.now(), LocalTime.now(), LocalDate.of(2020, 6, 25)
        );

        OperationValidator operationValidator = new OperationValidator(moneyAccount);
        operationValidator.validate(operation);
    }

    @Test(expected = ValidateException.class)
    public void incorrectAmountTest(){
        Operation operation = createOperationAndInitMoneyAccount(
                "256.4578", LocalDate.now(), LocalTime.now(), LocalDate.of(2020, 6, 25)
        );

        OperationValidator operationValidator = new OperationValidator(moneyAccount);
        operationValidator.validate(operation);
    }

    @Test(expected = ValidateException.class)
    public void incorrectDateTest(){
        Operation operation = createOperationAndInitMoneyAccount(
                "256.45", LocalDate.now(), LocalTime.now(), LocalDate.now().plusDays(2)
        );

        OperationValidator operationValidator = new OperationValidator(moneyAccount);
        operationValidator.validate(operation);
    }

    @Test(expected = ValidateException.class)
    public void incorrectDateTimeTest(){
        Operation operation = createOperationAndInitMoneyAccount(
                "256.45", LocalDate.now().plusDays(1), LocalTime.now(),
                LocalDate.of(2020, 6, 25)
        );

        OperationValidator operationValidator = new OperationValidator(moneyAccount);
        operationValidator.validate(operation);
    }

    private Operation createOperationAndInitMoneyAccount(String amount, LocalDate operationDate,
                                                         LocalTime operationTime, LocalDate dateOfCreatedMoneyAccount){

        moneyAccount = new MoneyAccount();
        moneyAccount.setDateCreated(dateOfCreatedMoneyAccount);

        Operation operation = new Operation();
        operation.setAmount(new BigDecimal(amount));
        operation.setDate(operationDate);
        operation.setTime(operationTime);

        return operation;
    }
}