package ru.market.domain.repository.account;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.CardAccount;

public interface CardAccountRepository extends AccountRepository<CardAccount> {
    @Query("select c from CardAccount c where c.number = :number")
    CardAccount findByNumber(@Param("number") String number);
}
