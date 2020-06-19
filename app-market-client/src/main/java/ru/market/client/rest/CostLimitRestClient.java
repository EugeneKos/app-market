package ru.market.client.rest;

import ru.market.client.exception.RestClientException;

import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitInfoDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

import java.time.LocalDate;
import java.util.Set;

public interface CostLimitRestClient {
    CostLimitDTO create(CostLimitNoIdDTO costLimitNoIdDTO) throws RestClientException;
    Set<CostLimitDTO> getAll() throws RestClientException;
    CostLimitInfoDTO getCostLimitInfoById(Long id, LocalDate date) throws RestClientException;
    void deleteById(Long id, Boolean rollbackOperations) throws RestClientException;
}
