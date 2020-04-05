package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.OperationConverter;
import ru.market.domain.repository.common.OperationRepository;
import ru.market.domain.service.IOperationService;
import ru.market.domain.service.operation.OperationHandler;

import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationResultDTO;
import ru.market.dto.operation.OperationTransferDTO;

import java.util.Set;
import java.util.stream.Collectors;

public class OperationServiceImpl implements IOperationService {
    private OperationRepository operationRepository;
    private OperationConverter operationConverter;

    private OperationHandler operationHandler;

    public OperationServiceImpl(OperationRepository operationRepository,
                                OperationConverter operationConverter,
                                OperationHandler operationHandler) {

        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
        this.operationHandler = operationHandler;
    }

    @Transactional
    @Override
    public OperationResultDTO enrollment(OperationEnrollDebitDTO enrollDebitDTO) {
        return operationHandler.enrollment(enrollDebitDTO).execute();
    }

    @Transactional
    @Override
    public OperationResultDTO debit(OperationEnrollDebitDTO enrollDebitDTO) {
        return operationHandler.debit(enrollDebitDTO).execute();
    }

    @Transactional
    @Override
    public OperationResultDTO transfer(OperationTransferDTO transferDTO) {
        return operationHandler.transfer(transferDTO).execute();
    }

    @Override
    public Set<OperationDTO> getAllByAccountId(Long accountId) {
        return operationRepository.findAllByAccountId(accountId)
                .stream()
                .map(operationConverter::convertToDTO)
                .collect(Collectors.toSet());
    }
}
