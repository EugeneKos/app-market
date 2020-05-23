package ru.market.dto.operation;

public class OperationEnrollDebitDTO extends OperationBasedDTO {
    private Long moneyAccountId;
    private String description;

    public Long getMoneyAccountId() {
        return moneyAccountId;
    }

    public void setMoneyAccountId(Long moneyAccountId) {
        this.moneyAccountId = moneyAccountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
