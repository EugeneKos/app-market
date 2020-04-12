package ru.market.domain.service.operation.impl;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.enumeration.OperationType;
import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.repository.common.OperationRepository;

import ru.market.domain.service.operation.OperationExecutor;
import ru.market.dto.operation.OperationResultDTO;

import java.util.function.BiFunction;

public class EnrollDebitOperationExecutor extends AbstractOperationExecutor implements OperationExecutor {
    private final BankAccount bankAccount;

    private Operation operation;
    private OperationType operationType;

    private BiFunction<BankAccount, Operation, OperationResultDTO> function;

    public EnrollDebitOperationExecutor(OperationRepository operationRepository, BankAccountRepository bankAccountRepository,
                                        BankAccount bankAccount, Operation operation, OperationType operationType,
                                        BiFunction<BankAccount, Operation, OperationResultDTO> function) {

        super(operationRepository, bankAccountRepository);

        this.bankAccount = bankAccount;
        this.operation = operation;
        this.operationType = operationType;
        this.function = function;
    }

    public OperationResultDTO execute(){
        synchronized (bankAccount){
            operation.setOperationType(operationType);

            OperationResultDTO result = function.apply(bankAccount, operation);

            if(!result.isSuccess()){
                return result;
            }

            saveAndUpdate(bankAccount, operation);

            return result;
        }
    }
}
