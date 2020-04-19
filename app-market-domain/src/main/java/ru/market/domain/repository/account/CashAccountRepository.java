package ru.market.domain.repository.account;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.CashAccount;

public interface CashAccountRepository extends AccountRepository<CashAccount> {
    @Query("select c from CashAccount c where c.name = :name")
    CashAccount findByName(@Param("name") String name);
}
