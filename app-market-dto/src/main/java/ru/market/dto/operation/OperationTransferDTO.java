package ru.market.dto.operation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "operationTransferBuilder")
public class OperationTransferDTO extends OperationBasedDTO {
    private Long fromMoneyAccountId;
    private Long toMoneyAccountId;
}
