package ru.market.client.rest;

import ru.market.client.exception.RestClientException;

import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationFilterDTO;
import ru.market.dto.operation.OperationTransferDTO;
import ru.market.dto.result.ResultDTO;

import java.util.Set;

public interface OperationRestClient {
    ResultDTO enrollment(OperationEnrollDebitDTO enrollDebitDTO) throws RestClientException;
    ResultDTO debit(OperationEnrollDebitDTO enrollDebitDTO) throws RestClientException;
    ResultDTO transfer(OperationTransferDTO transferDTO) throws RestClientException;

    Set<OperationDTO> getAllByMoneyAccountId(Long moneyAccountId) throws RestClientException;
    Set<OperationDTO> getAllByMoneyAccountIdAndFilter(Long moneyAccountId, OperationFilterDTO filterDTO) throws RestClientException;
}
