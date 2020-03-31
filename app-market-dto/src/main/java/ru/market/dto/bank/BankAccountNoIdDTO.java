package ru.market.dto.bank;

import java.util.Objects;

public class BankAccountNoIdDTO {
    private String identify;
    private String balance;
    private String description;
    private String bankAccountType;

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccountNoIdDTO dto = (BankAccountNoIdDTO) o;
        return Objects.equals(identify, dto.identify);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identify);
    }
}
