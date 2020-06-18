package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.CostConverter;
import ru.market.domain.converter.DateTimeConverter;
import ru.market.domain.data.Cost;
import ru.market.domain.data.CostLimit;
import ru.market.domain.data.Operation;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.repository.CostRepository;
import ru.market.domain.service.ICostLimitService;
import ru.market.domain.service.ICostService;
import ru.market.domain.service.IOperationService;
import ru.market.domain.validator.CommonValidator;
import ru.market.domain.validator.limit.CostValidator;

import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;
import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;

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

        CostLimit costLimit = costLimitService.getCostLimitById(costNoIdDTO.getCostLimitId());

        CommonValidator<Cost> validator = new CostValidator(costLimit);
        validator.validate(cost);

        cost.setCostLimit(costLimit);
        cost.setOperation(createOperation(costNoIdDTO));

        cost = costRepository.saveAndFlush(cost);
        return costConverter.convertToDTO(cost);
    }

    private Operation createOperation(CostNoIdDTO costNoIdDTO){
        OperationEnrollDebitDTO enrollDebitDTO = costConverter.convertToOperationEnrollDebitDTO(costNoIdDTO);

        OperationDTO operationDTO = operationService.debit(enrollDebitDTO);

        return operationService.getOperationById(operationDTO.getId());
    }

    @Transactional
    @Override
    public CostDTO update(CostDTO costDTO) {
        if(costDTO == null){
            return null;
        }

        Cost gettingById = getCostById(costDTO.getId());
        Cost cost = costConverter.convertToEntity(costDTO);

        CommonValidator<Cost> validator = new CostValidator(gettingById.getCostLimit());
        validator.validate(cost);

        if(isRequiredRollback(costDTO, gettingById)){
            operationService.rollback(gettingById.getOperation());
            return create(costDTO);
        }

        updateAndSave(cost, gettingById);

        return costConverter.convertToDTO(cost);
    }

    private void updateAndSave(Cost newCost, Cost oldCost){
        Operation operation = oldCost.getOperation();

        operation.setDescription(newCost.getDescription());
        operation.setDate(newCost.getDate());
        operation.setTime(newCost.getTime());

        operationService.update(operation);

        newCost.setCostLimit(oldCost.getCostLimit());
        newCost.setOperation(operation);

        costRepository.saveAndFlush(newCost);
    }

    private boolean isRequiredRollback(CostDTO newCost, Cost oldCost){
        if(oldCost.getSum().doubleValue() != Double.parseDouble(newCost.getSum())){
            return true;
        }

        return !oldCost.getOperation().getMoneyAccount().getId().equals(newCost.getMoneyAccountId());
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

    @Override
    public Set<Long> getAllIdByCostLimitIds(Set<Long> costLimitIds) {
        return costRepository.findAllIdByCostLimitIds(costLimitIds);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Cost cost = getCostById(id);
        costRepository.deleteById(id);
        operationService.rollback(cost.getOperation());
    }
}
