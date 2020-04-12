package ru.market.domain.service.operation.impl;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.enumeration.OperationType;

import ru.market.domain.exception.NotFoundException;

import ru.market.domain.service.operation.OperationAdditionalServices;
import ru.market.domain.service.operation.OperationExecutor;
import ru.market.domain.service.operation.OperationPrepare;

import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationResultDTO;

import java.util.function.BiFunction;

public class EnrollDebitOperationPrepare implements OperationPrepare {
    private OperationAdditionalServices operationAdditionalServices;

    private OperationEnrollDebitDTO enrollDebitDTO;
    private OperationType operationType;

    private BiFunction<BankAccount, Operation, OperationResultDTO> function;

    EnrollDebitOperationPrepare(OperationAdditionalServices operationAdditionalServices,
                                OperationEnrollDebitDTO enrollDebitDTO, OperationType operationType,
                                BiFunction<BankAccount, Operation, OperationResultDTO> function) {

        this.operationAdditionalServices = operationAdditionalServices;
        this.enrollDebitDTO = enrollDebitDTO;
        this.operationType = operationType;
        this.function = function;
    }

    @Override
    public OperationExecutor prepare() {
        Operation operation = operationAdditionalServices.operationConverter().convertToEntity(enrollDebitDTO);

        operationAdditionalServices.validator().validate(operation);

        BankAccount bankAccount = operationAdditionalServices.bankAccountRepository()
                .findById(enrollDebitDTO.getAccountId()).orElseThrow(
                        () -> new NotFoundException("Account with id " + enrollDebitDTO.getAccountId() + " not found"));

        return new EnrollDebitOperationExecutor(operationAdditionalServices, bankAccount, operation, operationType, function);
    }
}
