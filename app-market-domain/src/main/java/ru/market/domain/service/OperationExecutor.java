package ru.market.domain.service;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.enumeration.OperationType;

import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationResultDTO;
import ru.market.dto.operation.OperationTransferDTO;

import java.util.function.BiFunction;

public interface OperationExecutor {
    OperationResultDTO execute(OperationEnrollDebitDTO enrollDebitDTO, OperationType operationType,
                                      BiFunction<BankAccount, Operation, OperationResultDTO> function);

    OperationResultDTO execute(OperationTransferDTO transferDTO);
}
