package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.CostLimit;
import ru.market.domain.data.Person;

import java.util.Set;

public interface CostLimitRepository extends JpaRepository<CostLimit, Long> {
    @Query("select cl from CostLimit cl where cl.person = :person")
    Set<CostLimit> findAllByPerson(@Param("person")Person person);
}
