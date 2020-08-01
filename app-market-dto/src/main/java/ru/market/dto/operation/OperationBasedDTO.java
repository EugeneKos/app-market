package ru.market.dto.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class OperationBasedDTO {
    private String dateStr;
    private String timeStr;
    private String amount;
}
