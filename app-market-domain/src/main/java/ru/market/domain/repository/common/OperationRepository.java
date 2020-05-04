package ru.market.domain.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.Operation;

import java.util.Set;

public interface OperationRepository extends JpaRepository<Operation, Long>, JpaSpecificationExecutor<Operation> {
    @Query("select o from Operation o where o.bankAccount.id = :accountId")
    Set<Operation> findAllByAccountId(@Param("accountId") Long accountId);
}
