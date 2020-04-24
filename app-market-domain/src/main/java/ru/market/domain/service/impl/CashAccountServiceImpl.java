package ru.market.domain.service.impl;

import org.springframework.context.ApplicationEventPublisher;

import ru.market.domain.converter.AbstractDefaultConverter;
import ru.market.domain.data.CashAccount;
import ru.market.domain.repository.account.AccountRepository;
import ru.market.domain.service.ICashAccountService;
import ru.market.domain.service.IPersonProvider;

import ru.market.domain.validator.CommonValidator;

import ru.market.dto.cash.CashAccountDTO;
import ru.market.dto.cash.CashAccountNoIdDTO;

public class CashAccountServiceImpl extends AbstractAccountService<CashAccount, CashAccountNoIdDTO, CashAccountDTO>
        implements ICashAccountService {

    public CashAccountServiceImpl(AccountRepository<CashAccount> accountRepository,
                                  AbstractDefaultConverter<CashAccount, CashAccountNoIdDTO, CashAccountDTO> abstractDefaultConverter,
                                  CommonValidator<CashAccount> validator,
                                  IPersonProvider personProvider,
                                  ApplicationEventPublisher eventPublisher) {

        super(accountRepository, abstractDefaultConverter, validator, personProvider, eventPublisher);
    }
}
