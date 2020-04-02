package ru.market.domain.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.CardAccount;
import ru.market.domain.data.Person;

import java.util.Set;

public interface CardAccountRepository extends AbstractAccountRepository<CardAccount> {
    @Query("select c from CardAccount c where c.person = :person")
    Set<CardAccount> findAllByPerson(@Param("person") Person person);

    @Query("select c.id from CardAccount c where c.person.id = :personId")
    Set<Long> findAllAccountIdByPersonId(@Param("personId") Long personId);

    @Query("delete from CardAccount c where c.id = :id")
    @Modifying
    void deleteById(@Param("id") Long id);

    @Query("delete from CardAccount c where c.person.id = :personId")
    @Modifying
    void deleteByPersonId(@Param("personId") Long personId);
}
