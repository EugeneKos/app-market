package ru.market.domain.service.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.AbstractDefaultConverter;
import ru.market.domain.data.BankAccount;
import ru.market.domain.repository.account.AccountRepository;
import ru.market.domain.service.IBankAccountService;
import ru.market.domain.service.IPersonProvider;

import ru.market.domain.validator.CommonValidator;

import ru.market.dto.bank.BankAccountDTO;
import ru.market.dto.bank.BankAccountNoIdDTO;

public class BankAccountServiceImpl extends AbstractAccountService<BankAccount, BankAccountNoIdDTO, BankAccountDTO>
        implements IBankAccountService {

    private AccountRepository<BankAccount> accountRepository;

    public BankAccountServiceImpl(AccountRepository<BankAccount> accountRepository,
                                  AbstractDefaultConverter<BankAccount, BankAccountNoIdDTO, BankAccountDTO> abstractDefaultConverter,
                                  CommonValidator<BankAccount> validator,
                                  IPersonProvider personProvider,
                                  ApplicationEventPublisher eventPublisher) {

        super(accountRepository, abstractDefaultConverter, validator, personProvider, eventPublisher);
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void deleteAllByPersonId(Long personId) {
        accountRepository.deleteByPersonId(personId);
    }
}
