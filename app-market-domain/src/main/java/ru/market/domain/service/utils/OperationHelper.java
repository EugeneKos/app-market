package ru.market.domain.service.utils;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;

import ru.market.dto.operation.OperationResultDTO;

import ru.market.utils.Calculate;

public final class OperationHelper {
    private OperationHelper(){}

    public static OperationResultDTO enrollment(BankAccount bankAccount, Operation operation){
        bankAccount.setBalance(Calculate.plus(bankAccount.getBalance(), operation.getAmount()));
        return new OperationResultDTO(true, "Зачисление выполнено");
    }

    public static OperationResultDTO debit(BankAccount bankAccount, Operation operation){
        String balance = Calculate.minus(bankAccount.getBalance(), operation.getAmount());

        if(Double.parseDouble(balance) < 0){
            return new OperationResultDTO(false, "Недостаточно средств");
        }

        bankAccount.setBalance(balance);
        return new OperationResultDTO(true, "Списание выполнено");
    }
}
