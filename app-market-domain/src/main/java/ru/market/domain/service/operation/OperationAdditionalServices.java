package ru.market.domain.service.operation;

import ru.market.domain.converter.OperationConverter;
import ru.market.domain.data.Operation;
import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.repository.common.OperationRepository;
import ru.market.domain.validator.CommonValidator;

public interface OperationAdditionalServices {
    OperationRepository operationRepository();
    OperationConverter operationConverter();
    BankAccountRepository bankAccountRepository();
    CommonValidator<Operation> validator();
}
