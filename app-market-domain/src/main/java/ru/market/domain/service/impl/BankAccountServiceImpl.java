package ru.market.domain.service.impl;

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

    public BankAccountServiceImpl(AccountRepository<BankAccount> accountRepository,
                                  AbstractDefaultConverter<BankAccount, BankAccountNoIdDTO, BankAccountDTO> abstractDefaultConverter,
                                  CommonValidator<BankAccount> validator,
                                  IPersonProvider personProvider) {

        super(accountRepository, abstractDefaultConverter, validator, personProvider);
    }
}
