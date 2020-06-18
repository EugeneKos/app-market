package ru.market.domain.service;

import ru.market.domain.data.Operation;
import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationFilterDTO;
import ru.market.dto.operation.OperationTransferDTO;

import java.util.Set;

public interface IOperationService {
    OperationDTO enrollment(OperationEnrollDebitDTO enrollDebitDTO);

    OperationDTO debit(OperationEnrollDebitDTO enrollDebitDTO);

    OperationDTO transfer(OperationTransferDTO transferDTO);

    Operation getOperationById(Long id);

    void rollback(Operation operation);

    void update(Operation operation);

    Set<OperationDTO> getAllByMoneyAccountId(Long moneyAccountId);

    Set<OperationDTO> getAllByMoneyAccountIdAndFilter(Long moneyAccountId, OperationFilterDTO filterDTO);
}
