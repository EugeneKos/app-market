package ru.market.dto.cash;

import ru.market.dto.bank.BankAccountNoIdDTO;

import java.util.Objects;

public class CashAccountNoIdDTO extends BankAccountNoIdDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashAccountNoIdDTO that = (CashAccountNoIdDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
