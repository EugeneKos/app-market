package ru.market.client.rest;

import ru.market.client.exception.RestClientException;

import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;

import java.util.Set;

public interface CostRestClient {
    CostDTO create(CostNoIdDTO costNoIdDTO) throws RestClientException;
    CostDTO update(CostDTO costDTO) throws RestClientException;
    Set<CostDTO> getAllByCostLimitIdAndDate(Long costLimitId, String dateStr) throws RestClientException;
    void deleteById(Long id) throws RestClientException;
}
