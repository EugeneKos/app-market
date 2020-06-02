package ru.market.domain.service.impl;

import org.springframework.data.jpa.domain.Specification;

import ru.market.domain.converter.OperationConverter;
import ru.market.domain.data.Operation;
import ru.market.domain.data.enumeration.OperationType;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.repository.OperationRepository;
import ru.market.domain.service.IOperationService;
import ru.market.domain.service.OperationExecutor;
import ru.market.domain.service.utils.OperationHelper;
import ru.market.domain.specification.OperationSpecification;

import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationFilterDTO;
import ru.market.dto.operation.OperationResultDTO;
import ru.market.dto.operation.OperationTransferDTO;

import ru.market.utils.MoneyAccountLockHolder;

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
    public OperationResultDTO enrollment(OperationEnrollDebitDTO enrollDebitDTO) {
        synchronized (MoneyAccountLockHolder.getMoneyAccountLockById(enrollDebitDTO.getMoneyAccountId())){
            return operationExecutor.execute(enrollDebitDTO, OperationType.ENROLLMENT, OperationHelper::enrollment);
        }
    }

    @Override
    public OperationResultDTO debit(OperationEnrollDebitDTO enrollDebitDTO) {
        synchronized (MoneyAccountLockHolder.getMoneyAccountLockById(enrollDebitDTO.getMoneyAccountId())){
            return operationExecutor.execute(enrollDebitDTO, OperationType.DEBIT, OperationHelper::debit);
        }
    }

    @Override
    public OperationResultDTO transfer(OperationTransferDTO transferDTO) {
        Long fromMoneyAccountId = transferDTO.getFromMoneyAccountId();
        Long toMoneyAccountId = transferDTO.getToMoneyAccountId();

        if(fromMoneyAccountId < toMoneyAccountId){
            synchronized (MoneyAccountLockHolder.getMoneyAccountLockById(fromMoneyAccountId)){
                synchronized (MoneyAccountLockHolder.getMoneyAccountLockById(toMoneyAccountId)){
                    return operationExecutor.execute(transferDTO);
                }
            }
        } else {
            synchronized (MoneyAccountLockHolder.getMoneyAccountLockById(toMoneyAccountId)){
                synchronized (MoneyAccountLockHolder.getMoneyAccountLockById(fromMoneyAccountId)){
                    return operationExecutor.execute(transferDTO);
                }
            }
        }
    }

    @Override
    public Operation getOperationById(Long id) {
        return operationRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Operation with id %d not found", id))
        );
    }

    @Override
    public OperationResultDTO rollback(Long id) {
        Operation operation = getOperationById(id);
        synchronized (MoneyAccountLockHolder.getMoneyAccountLockById(operation.getMoneyAccount().getId())){
            return operationExecutor.rollback(operation);
        }
    }

    @Override
    public Set<OperationDTO> getAllByMoneyAccountId(Long moneyAccountId) {
        return operationRepository.findAllByMoneyAccountId(moneyAccountId)
                .stream()
                .map(operationConverter::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OperationDTO> getAllByMoneyAccountIdAndFilter(Long moneyAccountId, OperationFilterDTO filterDTO) {
        Specification<Operation> specification = OperationSpecification.createSpecification(moneyAccountId, filterDTO);

        return operationRepository.findAll(specification)
                .stream()
                .map(operationConverter::convertToDTO)
                .collect(Collectors.toSet());
    }
}
