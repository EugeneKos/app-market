package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.market.domain.data.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
