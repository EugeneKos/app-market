package ru.market.domain.service.impl;

import ru.market.domain.converter.AbstractDefaultConverter;
import ru.market.domain.data.CashAccount;
import ru.market.domain.repository.account.AccountRepository;
import ru.market.domain.repository.account.CashAccountRepository;
import ru.market.domain.service.ICashAccountService;
import ru.market.domain.service.IPersonProvider;

import ru.market.domain.validator.CommonValidator;
import ru.market.domain.validator.account.cash.CashAccountValidator;
import ru.market.domain.validator.account.cash.CashAccountValidatorImpl;

import ru.market.dto.cash.CashAccountDTO;
import ru.market.dto.cash.CashAccountNoIdDTO;

public class CashAccountServiceImpl extends AbstractAccountService<CashAccount, CashAccountNoIdDTO, CashAccountDTO>
        implements ICashAccountService {

    private CashAccountRepository cashAccountRepository;

    public CashAccountServiceImpl(AccountRepository<CashAccount> accountRepository,
                                  AbstractDefaultConverter<CashAccount, CashAccountNoIdDTO, CashAccountDTO> abstractDefaultConverter,
                                  IPersonProvider personProvider) {

        super(accountRepository, abstractDefaultConverter, personProvider);

        this.cashAccountRepository = (CashAccountRepository) accountRepository;
    }

    @Override
    protected CommonValidator<CashAccount> validator() {
        return entity -> {
            CashAccountValidator<CashAccount> cashAccountValidator = new CashAccountValidatorImpl<>(cashAccountRepository);
            cashAccountValidator.validateBalance(entity);
            cashAccountValidator.validateUniqueName(entity);
        };
    }
}
