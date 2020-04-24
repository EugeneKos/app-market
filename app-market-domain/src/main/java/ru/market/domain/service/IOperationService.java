package ru.market.domain.service;

import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationResultDTO;
import ru.market.dto.operation.OperationTransferDTO;

import java.util.Set;

public interface IOperationService {
    OperationResultDTO enrollment(OperationEnrollDebitDTO enrollDebitDTO);

    OperationResultDTO debit(OperationEnrollDebitDTO enrollDebitDTO);

    OperationResultDTO transfer(OperationTransferDTO transferDTO);

    Set<OperationDTO> getAllByAccountId(Long accountId);

    void deleteAllByAccountId(Long accountId);
}