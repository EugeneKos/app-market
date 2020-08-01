package ru.market.dto.limit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CostLimitNoIdDTO {
    private String sum;
    private String description;
    private String beginDateStr;
    private String endDateStr;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CostLimitNoIdDTO that = (CostLimitNoIdDTO) o;
        return Objects.equals(sum, that.sum) &&
                Objects.equals(beginDateStr, that.beginDateStr) &&
                Objects.equals(endDateStr, that.endDateStr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, beginDateStr, endDateStr);
    }
}
