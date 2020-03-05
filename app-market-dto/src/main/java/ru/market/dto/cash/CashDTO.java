package ru.market.dto.cash;

import java.util.Objects;

public class CashDTO extends CashNoIdDTO {
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
        if (!super.equals(o)) return false;
        CashDTO cashDTO = (CashDTO) o;
        return Objects.equals(id, cashDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
