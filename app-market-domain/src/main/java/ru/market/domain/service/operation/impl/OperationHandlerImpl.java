package ru.market.domain.service.operation.impl;

import ru.market.domain.converter.OperationConverter;
import ru.market.domain.data.Operation;
import ru.market.domain.data.enumeration.OperationType;
import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.repository.common.OperationRepository;
import ru.market.domain.service.operation.OperationExecutor;
import ru.market.domain.service.operation.OperationHandler;
import ru.market.domain.service.utils.OperationHelper;
import ru.market.domain.validator.CommonValidator;
import ru.market.domain.validator.operation.OperationValidator;
import ru.market.domain.validator.operation.OperationValidatorImpl;

import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationTransferDTO;

public class OperationHandlerImpl implements OperationHandler {
    private OperationRepository operationRepository;
    private OperationConverter operationConverter;

    private BankAccountRepository bankAccountRepository;

    public OperationHandlerImpl(OperationRepository operationRepository,
                                OperationConverter operationConverter,
                                BankAccountRepository bankAccountRepository) {

        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public OperationExecutor enrollment(OperationEnrollDebitDTO enrollDebitDTO) {
        return new OperationEnrollDebitExecutor(operationRepository, operationConverter, validator(),
                enrollDebitDTO, OperationType.ENROLLMENT, OperationHelper::enrollment, bankAccountRepository);
    }

    @Override
    public OperationExecutor debit(OperationEnrollDebitDTO enrollDebitDTO) {
        return new OperationEnrollDebitExecutor(operationRepository, operationConverter, validator(),
                enrollDebitDTO, OperationType.DEBIT, OperationHelper::debit, bankAccountRepository);
    }

    @Override
    public OperationExecutor transfer(OperationTransferDTO transferDTO) {
        return new OperationTransferExecutor(operationRepository, operationConverter, validator(),
                transferDTO, bankAccountRepository);
    }

    private CommonValidator<Operation> validator(){
        return operation -> {
            OperationValidator operationValidator = new OperationValidatorImpl();
            operationValidator.validateAmount(operation);
        };
    }
}
