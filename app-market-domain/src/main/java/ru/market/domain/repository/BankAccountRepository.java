package ru.market.domain.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Person;

import java.util.Set;

public interface BankAccountRepository extends AbstractBankRepository<BankAccount> {
    @Query("select b from BankAccount b where b.identify = :identify")
    BankAccount findByIdentify(@Param("identify") String identify);

    @Query("select b from BankAccount b where b.person = :person")
    Set<BankAccount> findAllByPerson(@Param("person") Person person);

    @Query("select b.id from BankAccount b where b.person.id = :personId")
    Set<Long> findAllBankIdByPersonId(@Param("personId") Long personId);

    @Query("delete from BankAccount b where b.id = :id")
    @Modifying
    void deleteById(@Param("id") Long id);

    @Query("delete from BankAccount b where b.person.id = :personId")
    @Modifying
    void deleteByPersonId(@Param("personId") Long personId);
}
