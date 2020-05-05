package ru.market.domain.service.utils;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;

import ru.market.dto.result.ResultDTO;
import ru.market.dto.result.ResultStatus;

import java.math.BigDecimal;

public final class OperationHelper {
    private OperationHelper(){}

    public static ResultDTO enrollment(BankAccount bankAccount, Operation operation){
        BigDecimal oldBalance = bankAccount.getBalance();
        operation.setOldBalance(oldBalance);
        BigDecimal newBalance = oldBalance.add(operation.getAmount());
        bankAccount.setBalance(newBalance);
        operation.setNewBalance(newBalance);
        return new ResultDTO(ResultStatus.SUCCESS, "Зачисление выполнено");
    }

    public static ResultDTO debit(BankAccount bankAccount, Operation operation){
        BigDecimal oldBalance = bankAccount.getBalance();
        operation.setOldBalance(oldBalance);
        BigDecimal newBalance = oldBalance.subtract(operation.getAmount());

        if(newBalance.doubleValue() < 0){
            return new ResultDTO(ResultStatus.FAILED, "Недостаточно средств");
        }

        bankAccount.setBalance(newBalance);
        operation.setNewBalance(newBalance);
        return new ResultDTO(ResultStatus.SUCCESS, "Списание выполнено");
    }
}
