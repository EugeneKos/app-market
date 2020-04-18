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
import ru.market.utils.AccountLockHolder;

import java.util.Set;
import java.util.stream.Collectors;

public class OperationServiceImpl implements IOperationService {
    private OperationRepository operationRepository;
    private OperationConverter operationConverter;

    private OperationHandler operationHandler;

    private final Object lock = new Object();

    public OperationServiceImpl(OperationRepository operationRepository,
                                OperationConverter operationConverter,
                                OperationHandler operationHandler) {

        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
        this.operationHandler = operationHandler;
    }

    //@Transactional
    @Override
    public OperationResultDTO enrollment(OperationEnrollDebitDTO enrollDebitDTO) {
        synchronized (AccountLockHolder.getAccountLockById(enrollDebitDTO.getAccountId())){
            return operationHandler.enrollment(enrollDebitDTO).prepare().execute();
        }
    }

    //@Transactional
    @Override
    public OperationResultDTO debit(OperationEnrollDebitDTO enrollDebitDTO) {
        synchronized (AccountLockHolder.getAccountLockById(enrollDebitDTO.getAccountId())){
            return operationHandler.debit(enrollDebitDTO).prepare().execute();
        }
    }

    //@Transactional
    @Override
    public OperationResultDTO transfer(OperationTransferDTO transferDTO) {
        Long fromAccountId = transferDTO.getFromAccountId();
        Long toAccountId = transferDTO.getToAccountId();

        if(fromAccountId < toAccountId){
            synchronized (AccountLockHolder.getAccountLockById(fromAccountId)){
                synchronized (AccountLockHolder.getAccountLockById(toAccountId)){
                    return operationHandler.transfer(transferDTO).prepare().execute();
                }
            }
        } else {
            synchronized (AccountLockHolder.getAccountLockById(toAccountId)){
                synchronized (AccountLockHolder.getAccountLockById(fromAccountId)){
                    return operationHandler.transfer(transferDTO).prepare().execute();
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
