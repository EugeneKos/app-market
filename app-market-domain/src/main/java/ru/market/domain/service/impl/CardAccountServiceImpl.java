package ru.market.domain.service.impl;

import ru.market.domain.converter.AbstractDefaultConverter;
import ru.market.domain.data.CardAccount;
import ru.market.domain.repository.account.AccountRepository;
import ru.market.domain.service.ICardAccountService;
import ru.market.domain.service.IPersonProvider;

import ru.market.domain.validator.CommonValidator;

import ru.market.dto.card.CardAccountDTO;
import ru.market.dto.card.CardAccountNoIdDTO;

public class CardAccountServiceImpl extends AbstractAccountService<CardAccount, CardAccountNoIdDTO, CardAccountDTO>
        implements ICardAccountService {

    public CardAccountServiceImpl(AccountRepository<CardAccount> accountRepository,
                                  AbstractDefaultConverter<CardAccount, CardAccountNoIdDTO, CardAccountDTO> abstractDefaultConverter,
                                  CommonValidator<CardAccount> validator,
                                  IPersonProvider personProvider) {

        super(accountRepository, abstractDefaultConverter, validator, personProvider);
    }
}
