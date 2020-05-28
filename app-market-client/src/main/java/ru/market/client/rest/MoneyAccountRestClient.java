package ru.market.client.rest;

import ru.market.client.exception.RestClientException;

import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;

import java.util.Set;

public interface MoneyAccountRestClient {
    MoneyAccountDTO create(MoneyAccountNoIdDTO moneyAccountNoIdDTO) throws RestClientException;
    MoneyAccountDTO getById(Long id) throws RestClientException;
    void deleteById(Long id) throws RestClientException;
    Set<MoneyAccountDTO> getAll() throws RestClientException;
}
