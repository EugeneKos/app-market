package ru.market.domain.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.CashAccount;
import ru.market.domain.data.Person;

import java.util.Set;

public interface CashAccountRepository extends AbstractAccountRepository<CashAccount> {
    @Query("select c from CashAccount c where c.person = :person")
    Set<CashAccount> findAllByPerson(@Param("person") Person person);

    @Query("select c.id from CashAccount c where c.person.id = :personId")
    Set<Long> findAllAccountIdByPersonId(@Param("personId") Long personId);

    @Query("delete from CashAccount c where c.id = :id")
    @Modifying
    void deleteById(@Param("id") Long id);

    @Query("delete from CashAccount c where c.person.id = :personId")
    @Modifying
    void deleteByPersonId(@Param("personId") Long personId);
}
