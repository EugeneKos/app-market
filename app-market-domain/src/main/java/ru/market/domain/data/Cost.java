package ru.market.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "cost", uniqueConstraints = {
        @UniqueConstraint(name = "cost_operation_uq", columnNames = {"id", "operation_id"})
})
public class Cost {
    @Id
    @SequenceGenerator(name = "cost_id_seq", sequenceName = "cost_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cost_id_seq")
    private Long id;

    @Column(name = "sum", nullable = false)
    private BigDecimal sum;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "operation_id", foreignKey = @ForeignKey(name = "cost_operation_fk"))
    private Operation operation;

    @ManyToOne
    @JoinColumn(name = "cost_limit_id", foreignKey = @ForeignKey(name = "cost_cost_limit_fk"))
    private CostLimit costLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public CostLimit getCostLimit() {
        return costLimit;
    }

    public void setCostLimit(CostLimit costLimit) {
        this.costLimit = costLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cost cost = (Cost) o;
        return Objects.equals(id, cost.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cost{" +
                "id=" + id +
                ", sum=" + sum +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", operationId=" + (operation == null ? "[]" : operation.getId()) +
                ", costLimitId=" + (costLimit == null ? "[]" : costLimit.getId()) +
                '}';
    }
}
