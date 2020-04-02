package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Person;

import java.util.Set;

@NoRepositoryBean
public interface AbstractAccountRepository<Entity extends BankAccount> extends JpaRepository<Entity, Long> {
    @Query("select b from #{#entityName} b where b.person = :person")
    Set<Entity> findAllByPerson(@Param("person") Person person);

    @Query("select b.id from #{#entityName} b where b.person.id = :personId")
    Set<Long> findAllAccountIdByPersonId(@Param("personId") Long personId);

    @Query("delete from #{#entityName} b where b.id = :id")
    @Modifying
    void deleteById(@Param("id") Long id);

    @Query("delete from #{#entityName} b where b.person.id = :personId")
    @Modifying
    void deleteByPersonId(@Param("personId") Long personId);
}
