package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.market.domain.data.Cost;

public interface CostRepository extends JpaRepository<Cost, Long> {
}
