package ru.market.dto.cost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CostNoIdDTO {
    private String sum;
    private String description;
    private String category;
    private String dateStr;
    private String timeStr;
    private Long costLimitId;
    private Long moneyAccountId;
}
