package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.market.domain.data.Cash;

public interface CashRepository extends JpaRepository<Cash, Long> {
}
