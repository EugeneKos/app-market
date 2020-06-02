package ru.market.domain.service.impl;

import ru.market.domain.converter.CostConverter;
import ru.market.domain.data.Cost;
import ru.market.domain.repository.CostRepository;
import ru.market.domain.service.ICostLimitService;
import ru.market.domain.service.ICostService;
import ru.market.domain.service.IOperationService;

import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;

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

    @Override
    public CostDTO create(CostNoIdDTO costNoIdDTO) {
        if(costNoIdDTO == null){
            return null;
        }

        Cost cost = costConverter.convertToEntity(costNoIdDTO);

        cost.setCostLimit(costLimitService.getCostLimitById(costNoIdDTO.getCostLimitId()));

        cost = costRepository.saveAndFlush(cost);
        return costConverter.convertToDTO(cost);
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
    public void delete(Long id) {

    }
}
