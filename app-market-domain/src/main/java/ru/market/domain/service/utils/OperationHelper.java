package ru.market.domain.service.utils;

import ru.market.domain.data.MoneyAccount;
import ru.market.domain.data.Operation;

import ru.market.dto.operation.OperationResultDTO;
import ru.market.dto.result.ResultStatus;

import java.math.BigDecimal;

public final class OperationHelper {
    private OperationHelper(){}

    public static OperationResultDTO enrollment(MoneyAccount moneyAccount, Operation operation){
        BigDecimal oldBalance = moneyAccount.getBalance();
        operation.setOldBalance(oldBalance);
        BigDecimal newBalance = oldBalance.add(operation.getAmount());
        moneyAccount.setBalance(newBalance);
        operation.setNewBalance(newBalance);
        return new OperationResultDTO(ResultStatus.SUCCESS, "Зачисление выполнено");
    }

    public static OperationResultDTO debit(MoneyAccount moneyAccount, Operation operation){
        BigDecimal oldBalance = moneyAccount.getBalance();
        operation.setOldBalance(oldBalance);
        BigDecimal newBalance = oldBalance.subtract(operation.getAmount());

        if(newBalance.doubleValue() < 0){
            return new OperationResultDTO(ResultStatus.FAILED, "Недостаточно средств");
        }

        moneyAccount.setBalance(newBalance);
        operation.setNewBalance(newBalance);
        return new OperationResultDTO(ResultStatus.SUCCESS, "Списание выполнено");
    }
}
