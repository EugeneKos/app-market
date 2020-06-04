package ru.market.domain.service;

import ru.market.domain.data.CostLimit;

import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitInfoDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

import java.util.Set;

public interface ICostLimitService {
    CostLimitDTO create(CostLimitNoIdDTO costLimitNoIdDTO);
    CostLimit getCostLimitById(Long id);
    CostLimitInfoDTO getCostLimitInfoById(Long id, String dateStr);
    Set<CostLimitDTO> getAll();
    void deleteById(Long id);
}
