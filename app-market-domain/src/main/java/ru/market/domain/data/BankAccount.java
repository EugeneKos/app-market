package ru.market.domain.data;

import ru.market.domain.data.enumeration.BankAccountType;

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
import javax.persistence.UniqueConstraint;

import java.util.Objects;

@Entity
@Table(name = "bank_account", uniqueConstraints = {@UniqueConstraint(name = "identify_uq", columnNames = "identify")})
public class BankAccount {
    @Id
    @SequenceGenerator(name = "bank_account_id_seq", sequenceName = "bank_account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_id_seq")
    private Long id;

    @Column(name = "identify", nullable = false)
    private String identify;

    @Column(name = "balance", nullable = false)
    private String balance;

    @Column(name = "description")
    private String description;

    @Column(name = "bank_account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BankAccountType bankAccountType;

    @ManyToOne
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "bank_person_fk"))
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BankAccountType getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(BankAccountType bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(identify, that.identify);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, identify);
    }
}
