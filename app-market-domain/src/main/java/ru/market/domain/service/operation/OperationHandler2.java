package ru.market.domain.service.operation;

import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationTransferDTO;

public interface OperationHandler2 {
    OperationPrepare enrollment(OperationEnrollDebitDTO enrollDebitDTO);
    OperationPrepare debit(OperationEnrollDebitDTO enrollDebitDTO);
    OperationPrepare transfer(OperationTransferDTO transferDTO);
}
