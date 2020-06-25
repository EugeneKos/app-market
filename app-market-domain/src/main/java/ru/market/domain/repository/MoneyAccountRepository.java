package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.MoneyAccount;
import ru.market.domain.data.Person;

import java.util.Set;

public interface MoneyAccountRepository extends JpaRepository<MoneyAccount, Long> {
    @Query("select ma from MoneyAccount ma where ma.name = :name and ma.person = :person")
    MoneyAccount findByNameAndPerson(@Param("name") String name, @Param("person") Person person);

    @Query("select ma from MoneyAccount ma where ma.person.id = :personId")
    Set<MoneyAccount> findAllByPersonId(@Param("personId") Long personId);

    @Query("select ma.id from MoneyAccount ma where ma.person.id = :personId")
    Set<Long> findAllIdByPersonId(@Param("personId") Long personId);
}
