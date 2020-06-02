package ru.market.dto.limit;

public class CostLimitInfoDTO extends CostLimitDTO {
    private String remain;
    private String spendingPerDay;

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public String getSpendingPerDay() {
        return spendingPerDay;
    }

    public void setSpendingPerDay(String spendingPerDay) {
        this.spendingPerDay = spendingPerDay;
    }
}
