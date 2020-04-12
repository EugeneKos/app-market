package ru.market.domain.service.operation.impl;

import ru.market.domain.converter.OperationConverter;
import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.repository.common.OperationRepository;
import ru.market.domain.service.operation.OperationExecutor;
import ru.market.domain.service.operation.OperationPrepare;

import ru.market.dto.operation.OperationTransferDTO;

public class TransferOperationPrepare implements OperationPrepare {
    private OperationRepository operationRepository;
    private OperationConverter operationConverter;

    private BankAccountRepository bankAccountRepository;

    private OperationTransferDTO transferDTO;

    public TransferOperationPrepare(OperationRepository operationRepository, OperationConverter operationConverter,
                                    BankAccountRepository bankAccountRepository, OperationTransferDTO transferDTO) {

        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
        this.bankAccountRepository = bankAccountRepository;
        this.transferDTO = transferDTO;
    }

    @Override
    public OperationExecutor prepare() {
        Operation operation = operationConverter.convertToEntity(transferDTO);

        BankAccount fromAccount = bankAccountRepository.getOne(transferDTO.getFromAccountId());
        BankAccount toAccount = bankAccountRepository.getOne(transferDTO.getToAccountId());

        return new TransferOperationExecutor(operationRepository, bankAccountRepository,
                fromAccount, toAccount, operation
        );
    }
}
