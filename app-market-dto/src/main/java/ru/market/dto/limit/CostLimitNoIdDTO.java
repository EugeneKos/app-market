package ru.market.dto.limit;

import java.util.Objects;

public class CostLimitNoIdDTO {
    private String sum;
    private String description;
    private String beginDateStr;
    private String endDateStr;

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

    public String getBeginDateStr() {
        return beginDateStr;
    }

    public void setBeginDateStr(String beginDateStr) {
        this.beginDateStr = beginDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CostLimitNoIdDTO that = (CostLimitNoIdDTO) o;
        return Objects.equals(sum, that.sum) &&
                Objects.equals(beginDateStr, that.beginDateStr) &&
                Objects.equals(endDateStr, that.endDateStr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, beginDateStr, endDateStr);
    }
}
