package ru.market.domain.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("select p from Person p where p.username = :username")
    Person findByUsername(@Param("username") String username);

    @Query("delete from Person p where p.id = :id")
    @Modifying
    void deleteById(@Param("id") Long id);

    @Query("delete from Person p where p.username = :username")
    @Modifying
    void deleteByUsername(@Param("username") String username);
}
