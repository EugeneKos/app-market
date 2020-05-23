package ru.market.dto.money;

import java.util.Objects;

public class MoneyAccountDTO extends MoneyAccountNoIdDTO {
    private Long id;
    private String dateCreatedStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateCreatedStr() {
        return dateCreatedStr;
    }

    public void setDateCreatedStr(String dateCreatedStr) {
        this.dateCreatedStr = dateCreatedStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyAccountDTO that = (MoneyAccountDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getName());
    }
}
