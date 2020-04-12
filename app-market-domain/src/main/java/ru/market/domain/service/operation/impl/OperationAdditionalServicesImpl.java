package ru.market.domain.service.operation.impl;

import ru.market.domain.converter.OperationConverter;
import ru.market.domain.data.Operation;
import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.repository.common.OperationRepository;
import ru.market.domain.service.operation.OperationAdditionalServices;
import ru.market.domain.validator.CommonValidator;
import ru.market.domain.validator.operation.OperationValidator;
import ru.market.domain.validator.operation.OperationValidatorImpl;

public class OperationAdditionalServicesImpl implements OperationAdditionalServices {
    private OperationRepository operationRepository;
    private OperationConverter operationConverter;

    private BankAccountRepository bankAccountRepository;

    OperationAdditionalServicesImpl(OperationRepository operationRepository,
                                    OperationConverter operationConverter,
                                    BankAccountRepository bankAccountRepository) {

        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public OperationRepository operationRepository() {
        return operationRepository;
    }

    @Override
    public OperationConverter operationConverter() {
        return operationConverter;
    }

    @Override
    public BankAccountRepository bankAccountRepository() {
        return bankAccountRepository;
    }

    @Override
    public CommonValidator<Operation> validator() {
        return operation -> {
            OperationValidator operationValidator = new OperationValidatorImpl();
            operationValidator.validateAmount(operation);
        };
    }
}
