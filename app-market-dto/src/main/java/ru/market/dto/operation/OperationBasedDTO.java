package ru.market.dto.operation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class OperationBasedDTO {
    private String dateStr;
    private String timeStr;
    private String amount;
}
