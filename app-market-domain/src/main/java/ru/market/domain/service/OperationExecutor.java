package ru.market.domain.service;

import ru.market.domain.data.MoneyAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.enumeration.OperationType;

import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationTransferDTO;
import ru.market.dto.result.ResultDTO;

import java.util.function.BiFunction;

public interface OperationExecutor {
    ResultDTO execute(OperationEnrollDebitDTO enrollDebitDTO, OperationType operationType,
                      BiFunction<MoneyAccount, Operation, ResultDTO> function);

    ResultDTO execute(OperationTransferDTO transferDTO);
}
