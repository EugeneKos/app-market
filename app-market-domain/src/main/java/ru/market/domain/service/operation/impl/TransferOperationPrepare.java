package ru.market.domain.service.operation.impl;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;

import ru.market.domain.exception.NotFoundException;

import ru.market.domain.service.operation.OperationAdditionalServices;
import ru.market.domain.service.operation.OperationExecutor;
import ru.market.domain.service.operation.OperationPrepare;

import ru.market.dto.operation.OperationTransferDTO;

public class TransferOperationPrepare implements OperationPrepare {
    private OperationAdditionalServices operationAdditionalServices;

    private OperationTransferDTO transferDTO;

    TransferOperationPrepare(OperationAdditionalServices operationAdditionalServices,
                             OperationTransferDTO transferDTO) {

        this.operationAdditionalServices = operationAdditionalServices;
        this.transferDTO = transferDTO;
    }

    @Override
    public OperationExecutor prepare() {
        Operation operation = operationAdditionalServices.operationConverter().convertToEntity(transferDTO);

        operationAdditionalServices.validator().validate(operation);

        BankAccount fromAccount = operationAdditionalServices.bankAccountRepository()
                .findById(transferDTO.getFromAccountId()).orElseThrow(
                        () -> new NotFoundException("Account with id " + transferDTO.getFromAccountId() + " not found"));

        BankAccount toAccount = operationAdditionalServices.bankAccountRepository()
                .findById(transferDTO.getToAccountId()).orElseThrow(
                        () -> new NotFoundException("Account with id " + transferDTO.getToAccountId() + " not found"));

        return new TransferOperationExecutor(operationAdditionalServices, fromAccount, toAccount, operation);
    }
}
