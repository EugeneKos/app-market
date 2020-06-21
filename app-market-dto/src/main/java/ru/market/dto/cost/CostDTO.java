package ru.market.dto.cost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "costBuilder")
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
