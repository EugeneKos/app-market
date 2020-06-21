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
@Builder(builderMethodName = "operationEnrollBuilder")
public class OperationEnrollDebitDTO extends OperationBasedDTO {
    private Long moneyAccountId;
    private String description;
}
