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

    @Query("select cl from CostLimit cl join fetch cl.costs where cl.id = :id")
    CostLimit findByIdWithCosts(@Param("id") Long id);

    @Query("select cl from CostLimit cl where cl.sum = :sumL and cl.beginDate = :beginDate and cl.endDate = :endDate")
    CostLimit findBySumAndPeriod(@Param("sumL") BigDecimal sum,
                                 @Param("beginDate") LocalDate beginDate,
                                 @Param("endDate") LocalDate endDate);

    @Query("select cl.id from CostLimit cl where cl.person.id = :personId")
    Set<Long> findAllIdByPersonId(@Param("personId") Long personId);

    @Query("select sum (clc.sum) from CostLimit cl left join cl.costs clc where cl.id = :id")
    Optional<BigDecimal> sumAllCosts(@Param("id") Long id);

    @Query("select sum (clc.sum) from CostLimit cl left join cl.costs clc where cl.id = :id and clc.date = :date")
    Optional<BigDecimal> sumAllCostsByDate(@Param("id") Long id, @Param("date") LocalDate date);

    @Query("select sum (clc.sum) from CostLimit cl left join cl.costs clc" +
            " where cl.id = :id and clc.date between :beginDate and :endDate")
    Optional<BigDecimal> sumAllCostsByPeriod(@Param("id") Long id,
                                             @Param("beginDate") LocalDate beginDate,
                                             @Param("endDate") LocalDate endDate);
}
