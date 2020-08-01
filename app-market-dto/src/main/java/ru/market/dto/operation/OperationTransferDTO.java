package ru.market.dto.operation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "operationTransferBuilder")
@ToString(callSuper = true)
public class OperationTransferDTO extends OperationBasedDTO {
    private Long fromMoneyAccountId;
    private Long toMoneyAccountId;
}
