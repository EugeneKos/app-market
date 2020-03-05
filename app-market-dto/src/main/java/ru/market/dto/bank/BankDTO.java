package ru.market.dto.bank;

import java.util.Objects;

public class BankDTO extends BankNoIdDTO {
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
        BankDTO bankDTO = (BankDTO) o;
        return Objects.equals(id, bankDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
