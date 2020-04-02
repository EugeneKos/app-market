package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Person;

import java.util.Set;

public interface AbstractAccountRepository<E extends BankAccount> extends JpaRepository<E, Long> {
    //E findByIdentify(String identify);

    Set<E> findAllByPerson(Person person);

    Set<Long> findAllAccountIdByPersonId(Long personId);

    void deleteById(Long id);

    void deleteByPersonId(Long personId);
}
