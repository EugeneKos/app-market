package ru.market.domain.service;

import ru.market.domain.data.MoneyAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.enumeration.OperationType;

import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationTransferDTO;

import java.util.function.BiConsumer;

public interface OperationExecutor {
    OperationDTO execute(OperationEnrollDebitDTO enrollDebitDTO, OperationType operationType,
                         BiConsumer<MoneyAccount, Operation> consumer);

    OperationDTO execute(OperationTransferDTO transferDTO);

    void rollback(Operation operation);
}
