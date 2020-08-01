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
@Builder(builderMethodName = "operationEnrollBuilder")
@ToString(callSuper = true)
public class OperationEnrollDebitDTO extends OperationBasedDTO {
    private Long moneyAccountId;
    private String description;
}
