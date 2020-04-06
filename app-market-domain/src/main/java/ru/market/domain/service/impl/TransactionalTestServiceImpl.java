package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.data.BankAccount;
import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.service.ITransactionalTestService;

public class TransactionalTestServiceImpl implements ITransactionalTestService {
    private static final Integer DELTA = 2000;

    private BankAccountRepository bankAccountRepository;

    public TransactionalTestServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public void changeBalance(Long accountId) {
        System.out.println("---------------------------------------------------------------------------------------");

        BankAccount bankAccount = bankAccountRepository.findById(
                accountId).orElseThrow(() -> new NullPointerException("ERROR-25")
        );

        String balance = bankAccount.getBalance();

        balance = String.valueOf(Integer.parseInt(balance) + DELTA);

        bankAccount.setBalance(balance);

        bankAccountRepository.saveAndFlush(bankAccount);

        System.out.println("---------------------------------------------------------------------------------------");
    }

    @Transactional
    @Override
    public void transactionalChangeBalance(Long accountId) {
        changeBalance(accountId);
    }
}
