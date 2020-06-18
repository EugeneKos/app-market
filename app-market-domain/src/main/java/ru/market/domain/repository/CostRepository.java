package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.Cost;

import java.time.LocalDate;
import java.util.Set;

public interface CostRepository extends JpaRepository<Cost, Long> {
    @Query("select c from Cost c where c.costLimit.id = :costLimitId and c.date = :date")
    Set<Cost> findAllByCostLimitIdAndDate(@Param("costLimitId") Long costLimitId, @Param("date")LocalDate date);

    @Query("select c.id from Cost c where c.costLimit.id in :costLimitIds")
    Set<Long> findAllIdByCostLimitIds(@Param("costLimitIds") Set<Long> costLimitIds);
}
