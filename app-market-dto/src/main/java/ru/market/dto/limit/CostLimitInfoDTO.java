package ru.market.dto.limit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "costLimitInfoBuilder")
public class CostLimitInfoDTO extends CostLimitDTO {
    private String remain;
    private String spendingPerDay;
    private String availableToday;
}
