package ru.market.domain.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.domain.data.MoneyAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.exception.OperationExecuteException;

import java.math.BigDecimal;

public final class OperationHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationHelper.class);

    private OperationHelper(){}

    public static void enrollment(MoneyAccount moneyAccount, Operation operation){
        BigDecimal oldBalance = moneyAccount.getBalance();
        operation.setOldBalance(oldBalance);
        BigDecimal newBalance = oldBalance.add(operation.getAmount());
        LOGGER.debug("[ ENROLLMENT ] old balance = {} new balance = {} amount = {}", oldBalance, newBalance, operation.getAmount());
        moneyAccount.setBalance(newBalance);
        operation.setNewBalance(newBalance);
    }

    public static void debit(MoneyAccount moneyAccount, Operation operation){
        BigDecimal oldBalance = moneyAccount.getBalance();
        operation.setOldBalance(oldBalance);
        BigDecimal newBalance = oldBalance.subtract(operation.getAmount());
        LOGGER.debug("[ DEBIT ] old balance = {} new balance = {} amount = {}", oldBalance, newBalance, operation.getAmount());

        if(newBalance.doubleValue() < 0){
            throw new OperationExecuteException("Недостаточно средств для совершения операции");
        }

        moneyAccount.setBalance(newBalance);
        operation.setNewBalance(newBalance);
    }
}
