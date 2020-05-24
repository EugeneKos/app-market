package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.market.domain.data.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
