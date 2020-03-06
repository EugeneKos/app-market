package ru.market.dto.card;

import ru.market.dto.bank.BankNoIdDTO;

import java.util.Objects;

public class CardNoIdDTO extends BankNoIdDTO {
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardNoIdDTO that = (CardNoIdDTO) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
