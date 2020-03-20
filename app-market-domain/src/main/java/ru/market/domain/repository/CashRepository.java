package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.Cash;
import ru.market.domain.data.Person;

import java.util.Set;

public interface CashRepository extends JpaRepository<Cash, Long> {
    @Query("select c from Cash c where c.name = :name")
    Cash findByName(@Param("name") String name);

    @Query("select c from Cash c where c.person = :person")
    Set<Cash> findAllByPerson(@Param("person") Person person);

    @Query("select c.id from Cash c where c.person.id = :personId")
    Set<Long> findAllCashIdByPersonId(@Param("personId") Long personId);

    @Query("delete from Cash c where c.id = :id")
    @Modifying
    void deleteById(@Param("id") Long id);

    @Query("delete from Cash c where c.person.id = :personId")
    @Modifying
    void deleteByPersonId(@Param("personId") Long personId);
}
