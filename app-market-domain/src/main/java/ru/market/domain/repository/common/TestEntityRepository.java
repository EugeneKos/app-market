package ru.market.domain.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.market.domain.data.TestEntity;

public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
}
