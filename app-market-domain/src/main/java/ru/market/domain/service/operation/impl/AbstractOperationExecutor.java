package ru.market.domain.service.operation.impl;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;

import ru.market.domain.service.operation.OperationAdditionalServices;

abstract class AbstractOperationExecutor {
    private OperationAdditionalServices operationAdditionalServices;

    AbstractOperationExecutor(OperationAdditionalServices operationAdditionalServices) {
        this.operationAdditionalServices = operationAdditionalServices;
    }

    void saveAndUpdate(BankAccount bankAccount, Operation operation){
        operationAdditionalServices.bankAccountRepository().saveAndFlush(bankAccount);
        operation.setBankAccount(bankAccount);
        operationAdditionalServices.operationRepository().saveAndFlush(operation);
    }
}
