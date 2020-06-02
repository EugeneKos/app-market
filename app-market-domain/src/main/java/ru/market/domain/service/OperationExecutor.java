package ru.market.domain.service;

import ru.market.domain.data.MoneyAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.enumeration.OperationType;

import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationResultDTO;
import ru.market.dto.operation.OperationTransferDTO;

import java.util.function.BiFunction;

public interface OperationExecutor {
    OperationResultDTO execute(OperationEnrollDebitDTO enrollDebitDTO, OperationType operationType,
                               BiFunction<MoneyAccount, Operation, OperationResultDTO> function);

    OperationResultDTO execute(OperationTransferDTO transferDTO);

    OperationResultDTO rollback(Operation operation);
}
