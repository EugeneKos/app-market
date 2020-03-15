package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.Cash;

public interface CashRepository extends JpaRepository<Cash, Long> {
    @Query("select c from Cash c where c.name = :name")
    Cash findByName(@Param("name") String name);

    @Query("delete from Cash c where c.id = :id")
    @Modifying
    void deleteById(@Param("id") Long id);
}
