package ru.market.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.util.Objects;

@Entity
@Table(name = "card_account", uniqueConstraints = {@UniqueConstraint(name = "card_number_uq", columnNames = "number")})
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "account_card_bank"))
public class CardAccount extends BankAccount {

    @Column(name = "number", nullable = false)
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
        if (!super.equals(o)) return false;
        CardAccount that = (CardAccount) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), number);
    }
}
