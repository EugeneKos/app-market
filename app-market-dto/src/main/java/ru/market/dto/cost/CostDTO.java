package ru.market.dto.cost;

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
@Builder(builderMethodName = "costBuilder")
@ToString(callSuper = true)
public class CostDTO extends CostNoIdDTO {
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CostDTO costDTO = (CostDTO) o;
        return Objects.equals(id, costDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
