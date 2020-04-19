package ru.market.dto.card;

import ru.market.dto.bank.BankAccountNoIdDTO;

import java.util.Objects;

public class CardAccountNoIdDTO extends BankAccountNoIdDTO {
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
        CardAccountNoIdDTO that = (CardAccountNoIdDTO) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {

        return Objects.hash(number);
    }
}
