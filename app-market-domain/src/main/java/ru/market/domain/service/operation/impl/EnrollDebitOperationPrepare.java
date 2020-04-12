package ru.market.domain.service.operation.impl;

import ru.market.domain.converter.OperationConverter;
import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.enumeration.OperationType;
import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.repository.common.OperationRepository;
import ru.market.domain.service.operation.OperationExecutor;
import ru.market.domain.service.operation.OperationPrepare;

import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationResultDTO;

import java.util.function.BiFunction;

public class EnrollDebitOperationPrepare implements OperationPrepare {
    private OperationRepository operationRepository;
    private OperationConverter operationConverter;

    private BankAccountRepository bankAccountRepository;

    private OperationEnrollDebitDTO enrollDebitDTO;
    private OperationType operationType;

    private BiFunction<BankAccount, Operation, OperationResultDTO> function;

    public EnrollDebitOperationPrepare(OperationRepository operationRepository, OperationConverter operationConverter,
                                       BankAccountRepository bankAccountRepository, OperationEnrollDebitDTO enrollDebitDTO, OperationType operationType,
                                       BiFunction<BankAccount, Operation, OperationResultDTO> function) {

        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
        this.bankAccountRepository = bankAccountRepository;
        this.enrollDebitDTO = enrollDebitDTO;
        this.operationType = operationType;
        this.function = function;
    }

    @Override
    public OperationExecutor prepare() {
        Operation operation = operationConverter.convertToEntity(enrollDebitDTO);

        BankAccount bankAccount = bankAccountRepository.getOne(enrollDebitDTO.getAccountId());

        return new EnrollDebitOperationExecutor(operationRepository, bankAccountRepository,
                bankAccount, operation, operationType, function
        );
    }
}
