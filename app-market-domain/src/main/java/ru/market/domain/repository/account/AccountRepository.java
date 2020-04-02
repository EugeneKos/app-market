package ru.market.domain.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Person;

import java.util.Set;

@NoRepositoryBean
public interface AccountRepository<Entity extends BankAccount> extends JpaRepository<Entity, Long> {
    Set<Entity> findAllByPerson(@Param("person") Person person);

    Set<Long> findAllAccountIdByPersonId(@Param("personId") Long personId);

    void deleteById(@Param("id") Long id);

    void deleteByPersonId(@Param("personId") Long personId);
}
