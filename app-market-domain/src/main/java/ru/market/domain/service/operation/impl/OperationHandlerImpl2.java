package ru.market.domain.service.operation.impl;

import ru.market.domain.converter.OperationConverter;
import ru.market.domain.data.enumeration.OperationType;
import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.repository.common.OperationRepository;
import ru.market.domain.service.operation.OperationHandler2;
import ru.market.domain.service.operation.OperationPrepare;
import ru.market.domain.service.utils.OperationHelper;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationTransferDTO;

public class OperationHandlerImpl2 implements OperationHandler2 {
    private OperationRepository operationRepository;
    private OperationConverter operationConverter;

    private BankAccountRepository bankAccountRepository;

    public OperationHandlerImpl2(OperationRepository operationRepository,
                                 OperationConverter operationConverter,
                                 BankAccountRepository bankAccountRepository) {

        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public OperationPrepare enrollment(OperationEnrollDebitDTO enrollDebitDTO) {
        return new EnrollDebitOperationPrepare(operationRepository, operationConverter, bankAccountRepository,
                enrollDebitDTO, OperationType.ENROLLMENT, OperationHelper::enrollment
        );
    }

    @Override
    public OperationPrepare debit(OperationEnrollDebitDTO enrollDebitDTO) {
        return new EnrollDebitOperationPrepare(operationRepository, operationConverter, bankAccountRepository,
                enrollDebitDTO, OperationType.DEBIT, OperationHelper::debit
        );
    }

    @Override
    public OperationPrepare transfer(OperationTransferDTO transferDTO) {
        return new TransferOperationPrepare(operationRepository, operationConverter, bankAccountRepository, transferDTO);
    }
}
