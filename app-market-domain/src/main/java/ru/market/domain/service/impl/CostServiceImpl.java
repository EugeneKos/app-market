package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.CostConverter;
import ru.market.domain.converter.DateTimeConverter;
import ru.market.domain.data.Cost;
import ru.market.domain.data.Operation;
import ru.market.domain.exception.CostExecuteException;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.repository.CostRepository;
import ru.market.domain.service.ICostLimitService;
import ru.market.domain.service.ICostService;
import ru.market.domain.service.IOperationService;

import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationResultDTO;
import ru.market.dto.result.ResultStatus;

import java.util.Set;
import java.util.stream.Collectors;

public class CostServiceImpl implements ICostService {
    private CostRepository costRepository;
    private CostConverter costConverter;

    private ICostLimitService costLimitService;
    private IOperationService operationService;

    public CostServiceImpl(CostRepository costRepository,
                           CostConverter costConverter,
                           ICostLimitService costLimitService,
                           IOperationService operationService) {

        this.costRepository = costRepository;
        this.costConverter = costConverter;
        this.costLimitService = costLimitService;
        this.operationService = operationService;
    }

    @Transactional
    @Override
    public CostDTO create(CostNoIdDTO costNoIdDTO) {
        if(costNoIdDTO == null){
            return null;
        }

        Cost cost = costConverter.convertToEntity(costNoIdDTO);

        cost.setCostLimit(costLimitService.getCostLimitById(costNoIdDTO.getCostLimitId()));
        cost.setOperation(createOperation(costNoIdDTO));

        cost = costRepository.saveAndFlush(cost);
        return costConverter.convertToDTO(cost);
    }

    private Operation createOperation(CostNoIdDTO costNoIdDTO){
        OperationEnrollDebitDTO enrollDebitDTO = costConverter.convertToOperationEnrollDebitDTO(costNoIdDTO);

        OperationResultDTO operationResultDTO = operationService.debit(enrollDebitDTO);
        if(operationResultDTO.getStatus() == ResultStatus.FAILED){
            throw new CostExecuteException(String.format(
                    "Ошибка проведения затраты. Статус операции: %s Описание: %s",
                    operationResultDTO.getStatus(), operationResultDTO.getDescription()
            ));
        }

        return operationService.getOperationById(operationResultDTO.getOperationId());
    }

    @Transactional
    @Override
    public CostDTO update(CostDTO costDTO) {
        if(costDTO == null){
            return null;
        }

        Cost gettingById = getCostById(costDTO.getId());

        if(isRequiredRollback(costDTO, gettingById)){
            rollbackOperation(gettingById.getOperation().getId());
            return create(costDTO);
        }

        Cost cost = costConverter.convertToEntity(costDTO);
        cost = costRepository.saveAndFlush(cost);
        return costConverter.convertToDTO(cost);
    }

    private boolean isRequiredRollback(CostDTO newCost, Cost oldCost){
        if(!oldCost.getSum().toString().equals(newCost.getSum())){
            return true;
        }

        return !oldCost.getOperation().getMoneyAccount().getId().equals(newCost.getMoneyAccountId());
    }

    private void rollbackOperation(Long operationId){
        OperationResultDTO operationResultDTO = operationService.rollback(operationId);
        if(operationResultDTO.getStatus() == ResultStatus.FAILED){
            throw new CostExecuteException(String.format(
                    "Ошибка проведения затраты. Статус операции: %s Описание: %s",
                    operationResultDTO.getStatus(), operationResultDTO.getDescription()
            ));
        }
    }

    private Cost getCostById(Long id){
        return costRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Cost with id %d not found", id))
        );
    }

    @Override
    public Set<CostDTO> getAllByCostLimitIdAndDate(Long costLimitId, String dateStr) {
        return costRepository.findAllByCostLimitIdAndDate(costLimitId, DateTimeConverter.convertToLocalDate(dateStr))
                .stream()
                .map(costConverter::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Cost cost = getCostById(id);
        costRepository.deleteById(id);
        rollbackOperation(cost.getOperation().getId());
    }
}
