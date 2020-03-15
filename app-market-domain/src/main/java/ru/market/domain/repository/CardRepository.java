package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("select c from Card c where c.number = :number")
    Card findByNumber(@Param("number") String number);

    @Query("delete from Card c where c.id = :id")
    @Modifying
    void deleteById(@Param("id") Long id);
}
