package ru.market.domain.service.impl;

import ru.market.domain.converter.OperationConverter;
import ru.market.domain.data.enumeration.OperationType;
import ru.market.domain.repository.common.OperationRepository;
import ru.market.domain.service.IOperationService;
import ru.market.domain.service.OperationExecutor;
import ru.market.domain.service.utils.OperationHelper;

import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationTransferDTO;
import ru.market.dto.result.ResultDTO;

import ru.market.utils.AccountLockHolder;

import java.util.Set;
import java.util.stream.Collectors;

public class OperationServiceImpl implements IOperationService {
    private OperationRepository operationRepository;
    private OperationConverter operationConverter;

    private OperationExecutor operationExecutor;

    public OperationServiceImpl(OperationRepository operationRepository,
                                OperationConverter operationConverter,
                                OperationExecutor operationExecutor) {

        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
        this.operationExecutor = operationExecutor;
    }

    @Override
    public ResultDTO enrollment(OperationEnrollDebitDTO enrollDebitDTO) {
        synchronized (AccountLockHolder.getAccountLockById(enrollDebitDTO.getAccountId())){
            return operationExecutor.execute(enrollDebitDTO, OperationType.ENROLLMENT, OperationHelper::enrollment);
        }
    }

    @Override
    public ResultDTO debit(OperationEnrollDebitDTO enrollDebitDTO) {
        synchronized (AccountLockHolder.getAccountLockById(enrollDebitDTO.getAccountId())){
            return operationExecutor.execute(enrollDebitDTO, OperationType.DEBIT, OperationHelper::debit);
        }
    }

    @Override
    public ResultDTO transfer(OperationTransferDTO transferDTO) {
        Long fromAccountId = transferDTO.getFromAccountId();
        Long toAccountId = transferDTO.getToAccountId();

        if(fromAccountId < toAccountId){
            synchronized (AccountLockHolder.getAccountLockById(fromAccountId)){
                synchronized (AccountLockHolder.getAccountLockById(toAccountId)){
                    return operationExecutor.execute(transferDTO);
                }
            }
        } else {
            synchronized (AccountLockHolder.getAccountLockById(toAccountId)){
                synchronized (AccountLockHolder.getAccountLockById(fromAccountId)){
                    return operationExecutor.execute(transferDTO);
                }
            }
        }
    }

    @Override
    public Set<OperationDTO> getAllByAccountId(Long accountId) {
        return operationRepository.findAllByAccountId(accountId)
                .stream()
                .map(operationConverter::convertToDTO)
                .collect(Collectors.toSet());
    }
}
