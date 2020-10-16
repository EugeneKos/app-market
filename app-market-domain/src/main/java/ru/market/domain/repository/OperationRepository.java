package ru.market.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.market.domain.data.Operation;

import java.util.Set;

public interface OperationRepository extends JpaRepository<Operation, Long>, JpaSpecificationExecutor<Operation> {
    @Query("select o from Operation o where o.moneyAccount.id = :moneyAccountId order by o.date desc, o.time desc")
    Set<Operation> findAllByMoneyAccountId(@Param("moneyAccountId") Long moneyAccountId);
}
