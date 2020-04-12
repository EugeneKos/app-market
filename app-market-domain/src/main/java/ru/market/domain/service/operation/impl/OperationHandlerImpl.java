package ru.market.domain.service.operation.impl;

import ru.market.domain.converter.OperationConverter;
import ru.market.domain.data.enumeration.OperationType;
import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.repository.common.OperationRepository;
import ru.market.domain.service.operation.OperationAdditionalServices;
import ru.market.domain.service.operation.OperationHandler;
import ru.market.domain.service.operation.OperationPrepare;
import ru.market.domain.service.utils.OperationHelper;

import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationTransferDTO;

public class OperationHandlerImpl implements OperationHandler {
    private OperationAdditionalServices operationAdditionalServices;

    public OperationHandlerImpl(OperationRepository operationRepository,
                                OperationConverter operationConverter,
                                BankAccountRepository bankAccountRepository) {

        this.operationAdditionalServices = new OperationAdditionalServicesImpl(
                operationRepository, operationConverter, bankAccountRepository
        );
    }

    @Override
    public OperationPrepare enrollment(OperationEnrollDebitDTO enrollDebitDTO) {
        return new EnrollDebitOperationPrepare(operationAdditionalServices, enrollDebitDTO,
                OperationType.ENROLLMENT, OperationHelper::enrollment
        );
    }

    @Override
    public OperationPrepare debit(OperationEnrollDebitDTO enrollDebitDTO) {
        return new EnrollDebitOperationPrepare(operationAdditionalServices, enrollDebitDTO,
                OperationType.DEBIT, OperationHelper::debit
        );
    }

    @Override
    public OperationPrepare transfer(OperationTransferDTO transferDTO) {
        return new TransferOperationPrepare(operationAdditionalServices, transferDTO);
    }
}
