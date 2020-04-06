package ru.market.domain.service;

public interface ITransactionalTestService {
    void changeBalance(Long accountId);
    void transactionalChangeBalance(Long accountId);
}
