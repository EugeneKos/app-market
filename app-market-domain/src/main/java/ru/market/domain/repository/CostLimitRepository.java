package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.CostLimit;
import ru.market.domain.data.Person;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface CostLimitRepository extends JpaRepository<CostLimit, Long> {
    @Query("select cl from CostLimit cl where cl.person = :person")
    Set<CostLimit> findAllByPerson(@Param("person")Person person);

    @Query("select sum (clc.sum) from CostLimit cl left join cl.costs clc where cl.id = :id")
    Optional<BigDecimal> findAndSumAllCostsById(@Param("id") Long id);

    @Query("select sum (clc.sum) from CostLimit cl left join cl.costs clc where cl.id = :id and clc.date = :date")
    Optional<BigDecimal> findAndSumAllCostsByIdAndDate(@Param("id") Long id, @Param("date") LocalDate date);
}
