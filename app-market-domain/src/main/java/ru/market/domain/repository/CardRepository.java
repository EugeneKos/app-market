package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.market.domain.data.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
}
