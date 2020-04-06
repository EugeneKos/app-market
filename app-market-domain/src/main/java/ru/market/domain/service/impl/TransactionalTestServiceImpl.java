package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.data.TestEntity;
import ru.market.domain.repository.common.TestEntityRepository;
import ru.market.domain.service.ITransactionalTestService;

public class TransactionalTestServiceImpl implements ITransactionalTestService {
    private static final Integer DELTA = 2000;

    private TestEntityRepository testEntityRepository;

    public TransactionalTestServiceImpl(TestEntityRepository testEntityRepository) {
        this.testEntityRepository = testEntityRepository;
    }

    @Override
    public void changeBalance(Long accountId) {
        String threadName = Thread.currentThread().getName();

        System.out.println("-----------------------------" + threadName + " begin ----------------------------------------------------------");

        TestEntity testEntity = testEntityRepository.findById(
                accountId).orElseThrow(() -> new NullPointerException("ERROR-25")
        );

        String balance = testEntity.getBalance();

        balance = String.valueOf(Integer.parseInt(balance) + DELTA);

        testEntity.setBalance(balance);

        testEntityRepository.saveAndFlush(testEntity);

        System.out.println("-----------------------------" + threadName + " end ------------------------------------------------------------");
    }

    @Transactional
    @Override
    public void transactionalChangeBalance(Long accountId) {
        changeBalance(accountId);
    }
}
