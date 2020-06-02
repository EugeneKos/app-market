package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.CostConverter;
import ru.market.domain.data.Cost;
import ru.market.domain.data.Operation;
import ru.market.domain.exception.CostExecuteException;
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

        CostDTO costDTO = costConverter.convertToDTO(cost);

        costDTO.setCostLimitId(costNoIdDTO.getCostLimitId());
        costDTO.setMoneyAccountId(costNoIdDTO.getMoneyAccountId());

        return costDTO;
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

    @Override
    public CostDTO update(CostDTO costDTO) {
        return null;
    }

    @Override
    public Set<CostDTO> getAllByCostLimitIdAndDate(Long costLimitId, String dateStr) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
