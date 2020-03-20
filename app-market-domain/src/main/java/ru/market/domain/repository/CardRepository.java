package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.Card;
import ru.market.domain.data.Person;

import java.util.Set;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("select c from Card c where c.number = :number")
    Card findByNumber(@Param("number") String number);

    @Query("select c from Card c where c.person = :person")
    Set<Card> findAllByPerson(@Param("person") Person person);

    @Query("select c.id from Card c where c.person.id = :personId")
    Set<Long> findAllCardIdByPersonId(@Param("personId") Long personId);

    @Query("delete from Card c where c.id = :id")
    @Modifying
    void deleteById(@Param("id") Long id);

    @Query("delete from Card c where c.person.id = :personId")
    @Modifying
    void deleteByPersonId(@Param("personId") Long personId);
}
