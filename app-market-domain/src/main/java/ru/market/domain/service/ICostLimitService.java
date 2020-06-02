package ru.market.domain.service;

import ru.market.domain.data.CostLimit;

import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

import java.util.Set;

public interface ICostLimitService {
    CostLimitDTO create(CostLimitNoIdDTO costLimitNoIdDTO);
    CostLimit getCostLimitById(Long id);
    Set<CostLimitDTO> getAll();
    void delete(Long id);
}
