package ru.market.domain.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cost_limit", uniqueConstraints = {
        @UniqueConstraint(name = "cost_limit_uq", columnNames = {"sum", "begin_date", "end_date"})
})
public class CostLimit {
    @Id
    @SequenceGenerator(name = "cost_limit_id_seq", sequenceName = "cost_limit_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cost_limit_id_seq")
    private Long id;

    @Column(name = "sum", nullable = false)
    private BigDecimal sum;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "begin_date", nullable = false)
    private LocalDate beginDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "cost_limit_person_fk"))
    private Person person;

    @OneToMany(targetEntity = Cost.class, cascade = {CascadeType.REMOVE}, mappedBy = "costLimit")
    private Set<Cost> costs;

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

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Cost> getCosts() {
        return costs;
    }

    public void setCosts(Set<Cost> costs) {
        this.costs = costs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CostLimit costLimit = (CostLimit) o;
        return Objects.equals(id, costLimit.id) &&
                Objects.equals(sum, costLimit.sum) &&
                Objects.equals(beginDate, costLimit.beginDate) &&
                Objects.equals(endDate, costLimit.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sum, beginDate, endDate);
    }

    @Override
    public String toString() {
        return "CostLimit{" +
                "id=" + id +
                ", sum=" + sum +
                ", description='" + description + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", personId=" + (person == null ? "[]" : person.getId()) +
                '}';
    }
}
