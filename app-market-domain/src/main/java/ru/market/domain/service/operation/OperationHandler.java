package ru.market.domain.service.operation;

import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationTransferDTO;

public interface OperationHandler {
    OperationExecutor enrollment(OperationEnrollDebitDTO enrollDebitDTO);
    OperationExecutor debit(OperationEnrollDebitDTO enrollDebitDTO);
    OperationExecutor transfer(OperationTransferDTO transferDTO);
}
