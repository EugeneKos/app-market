package ru.market.domain.service.operation.impl;

import ru.market.domain.converter.OperationConverter;
import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.enumeration.OperationType;
import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.repository.common.OperationRepository;
import ru.market.domain.service.operation.OperationExecutor;
import ru.market.domain.validator.CommonValidator;

import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationResultDTO;

import java.util.function.BiFunction;

public class OperationEnrollDebitExecutor implements OperationExecutor {
    private OperationRepository operationRepository;
    private OperationConverter operationConverter;

    private CommonValidator<Operation> commonValidator;

    private OperationEnrollDebitDTO enrollDebitDTO;
    private OperationType operationType;

    private BiFunction<BankAccount, Operation, OperationResultDTO> process;

    private BankAccountRepository bankAccountRepository;

    OperationEnrollDebitExecutor(OperationRepository operationRepository,
                                        OperationConverter operationConverter,
                                        CommonValidator<Operation> commonValidator,
                                        OperationEnrollDebitDTO enrollDebitDTO,
                                        OperationType operationType,
                                        BiFunction<BankAccount, Operation, OperationResultDTO> process,
                                        BankAccountRepository bankAccountRepository) {

        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
        this.commonValidator = commonValidator;
        this.enrollDebitDTO = enrollDebitDTO;
        this.operationType = operationType;
        this.process = process;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public OperationResultDTO execute() {
        Operation operation = operationConverter.convertToEntity(enrollDebitDTO);

        commonValidator.validate(operation);

        operation.setOperationType(operationType);

        BankAccount bankAccount = bankAccountRepository.getOne(enrollDebitDTO.getAccountId());

        OperationResultDTO resultDTO = process.apply(bankAccount, operation);

        if(!resultDTO.isSuccess()){
            return resultDTO;
        }

        bankAccountRepository.saveAndFlush(bankAccount);
        operation.setBankAccount(bankAccount);
        operationRepository.saveAndFlush(operation);

        return resultDTO;
    }
}