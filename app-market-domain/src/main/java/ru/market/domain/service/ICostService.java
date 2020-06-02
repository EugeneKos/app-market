package ru.market.domain.service;

import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;

import java.util.Set;

public interface ICostService {
    CostDTO create(CostNoIdDTO costNoIdDTO);
    CostDTO update(CostDTO costDTO);
    Set<CostDTO> getAllByCostLimitIdAndDate(Long costLimitId, String dateStr);
    void delete(Long id);
}