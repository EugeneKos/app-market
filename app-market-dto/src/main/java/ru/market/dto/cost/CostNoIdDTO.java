package ru.market.dto.cost;

public class CostNoIdDTO {
    private String sum;
    private String description;
    private String category;
    private String dateStr;
    private String timeStr;
    private Long costLimitId;
    private Long moneyAccountId;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Long getCostLimitId() {
        return costLimitId;
    }

    public void setCostLimitId(Long costLimitId) {
        this.costLimitId = costLimitId;
    }

    public Long getMoneyAccountId() {
        return moneyAccountId;
    }

    public void setMoneyAccountId(Long moneyAccountId) {
        this.moneyAccountId = moneyAccountId;
    }
}
