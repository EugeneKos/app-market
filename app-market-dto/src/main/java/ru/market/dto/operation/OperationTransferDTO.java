package ru.market.dto.operation;

public class OperationTransferDTO extends OperationBasedDTO {
    private Long fromMoneyAccountId;
    private Long toMoneyAccountId;

    public Long getFromMoneyAccountId() {
        return fromMoneyAccountId;
    }

    public void setFromMoneyAccountId(Long fromMoneyAccountId) {
        this.fromMoneyAccountId = fromMoneyAccountId;
    }

    public Long getToMoneyAccountId() {
        return toMoneyAccountId;
    }

    public void setToMoneyAccountId(Long toMoneyAccountId) {
        this.toMoneyAccountId = toMoneyAccountId;
    }
}
