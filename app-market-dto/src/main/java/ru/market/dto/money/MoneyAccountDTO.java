package ru.market.dto.money;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "moneyAccountBuilder")
@ToString(callSuper = true)
public class MoneyAccountDTO extends MoneyAccountNoIdDTO {
    private Long id;
    private String dateCreatedStr;

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
