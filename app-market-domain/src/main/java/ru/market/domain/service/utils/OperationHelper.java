package ru.market.domain.service.utils;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;

import ru.market.dto.operation.OperationResultDTO;

import ru.market.utils.Calculate;

public final class OperationHelper {
    private OperationHelper(){}

    public static OperationResultDTO enrollment(BankAccount bankAccount, Operation operation){
        String oldBalance = bankAccount.getBalance();
        operation.setOldBalance(oldBalance);
        String newBalance = Calculate.plus(oldBalance, operation.getAmount());
        bankAccount.setBalance(newBalance);
        operation.setNewBalance(newBalance);
        return new OperationResultDTO(true, "Зачисление выполнено");
    }

    public static OperationResultDTO debit(BankAccount bankAccount, Operation operation){
        String oldBalance = bankAccount.getBalance();
        operation.setOldBalance(oldBalance);
        String newBalance = Calculate.minus(oldBalance, operation.getAmount());

        if(Double.parseDouble(newBalance) < 0){
            return new OperationResultDTO(false, "Недостаточно средств");
        }

        bankAccount.setBalance(newBalance);
        operation.setNewBalance(newBalance);
        return new OperationResultDTO(true, "Списание выполнено");
    }
}
