package ru.market.domain.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.Operation;

import java.util.Set;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("select o from Operation o where o.bankAccount.id = :accountId")
    Set<Operation> findAllByAccountId(@Param("accountId") Long accountId);

    @Query("delete from Operation o where o.bankAccount.id = :accountId")
    @Modifying
    void deleteByAccountId(@Param("accountId") Long accountId);
}
