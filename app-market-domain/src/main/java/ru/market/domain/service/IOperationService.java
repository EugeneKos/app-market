package ru.market.domain.service;

import ru.market.domain.data.Operation;
import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationFilterDTO;
import ru.market.dto.operation.OperationResultDTO;
import ru.market.dto.operation.OperationTransferDTO;

import java.util.Set;

public interface IOperationService {
    OperationResultDTO enrollment(OperationEnrollDebitDTO enrollDebitDTO);

    OperationResultDTO debit(OperationEnrollDebitDTO enrollDebitDTO);

    OperationResultDTO transfer(OperationTransferDTO transferDTO);

    Operation getOperationById(Long id);

    OperationResultDTO rollback(Long id);

    Set<OperationDTO> getAllByMoneyAccountId(Long moneyAccountId);

    Set<OperationDTO> getAllByMoneyAccountIdAndFilter(Long moneyAccountId, OperationFilterDTO filterDTO);
}
