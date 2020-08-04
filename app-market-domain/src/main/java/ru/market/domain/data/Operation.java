package ru.market.domain.data;

import ru.market.domain.data.enumeration.OperationType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "operation")
public class Operation {
    @Id
    @SequenceGenerator(name = "operation_id_seq", sequenceName = "operation_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_id_seq")
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "operation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "new_balance", nullable = false)
    private BigDecimal newBalance;

    @Column(name = "old_balance", nullable = false)
    private BigDecimal oldBalance;

    @ManyToOne
    @JoinColumn(name = "money_account_id", foreignKey = @ForeignKey(name = "operation_money_account_fk"))
    private MoneyAccount moneyAccount;

    public Operation(){
    }

    private Operation(LocalDate date, LocalTime time, BigDecimal amount){
        this.date = date;
        this.time = time;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    public BigDecimal getOldBalance() {
        return oldBalance;
    }

    public void setOldBalance(BigDecimal oldBalance) {
        this.oldBalance = oldBalance;
    }

    public MoneyAccount getMoneyAccount() {
        return moneyAccount;
    }

    public void setMoneyAccount(MoneyAccount moneyAccount) {
        this.moneyAccount = moneyAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(id, operation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", amount=" + amount +
                ", operationType=" + operationType +
                ", description='" + description + '\'' +
                ", newBalance=" + newBalance +
                ", oldBalance=" + oldBalance +
                ", moneyAccountId=" + (moneyAccount == null ? "[]" : moneyAccount.getId()) +
                '}';
    }

    public Operation customClone() {
        return new Operation(
                LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth()),
                LocalTime.of(time.getHour(), time.getMinute()),
                new BigDecimal(amount.doubleValue())
        );
    }
}
