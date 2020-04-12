package ru.market.domain.service.operation.impl;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;

import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.repository.common.OperationRepository;

abstract class AbstractOperationExecutor {
    private OperationRepository operationRepository;
    private BankAccountRepository bankAccountRepository;

    AbstractOperationExecutor(OperationRepository operationRepository,
                              BankAccountRepository bankAccountRepository) {

        this.operationRepository = operationRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    void saveAndUpdate(BankAccount bankAccount, Operation operation){
        bankAccountRepository.saveAndFlush(bankAccount);
        operation.setBankAccount(bankAccount);
        operationRepository.saveAndFlush(operation);
    }
}
