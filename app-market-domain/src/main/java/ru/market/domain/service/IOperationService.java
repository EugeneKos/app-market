package ru.market.domain.service;

import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationFilterDTO;
import ru.market.dto.operation.OperationTransferDTO;
import ru.market.dto.result.ResultDTO;

import java.util.Set;

public interface IOperationService {
    ResultDTO enrollment(OperationEnrollDebitDTO enrollDebitDTO);

    ResultDTO debit(OperationEnrollDebitDTO enrollDebitDTO);

    ResultDTO transfer(OperationTransferDTO transferDTO);

    Set<OperationDTO> getAllByMoneyAccountId(Long moneyAccountId);

    Set<OperationDTO> getAllByMoneyAccountIdAndFilter(Long moneyAccountId, OperationFilterDTO filterDTO);
}
