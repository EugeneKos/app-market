package ru.market.dto.cost;

import java.util.Objects;

public class CostDTO extends CostNoIdDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
