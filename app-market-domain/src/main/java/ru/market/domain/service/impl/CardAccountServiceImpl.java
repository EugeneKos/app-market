package ru.market.domain.service.impl;

import ru.market.domain.converter.AbstractDefaultConverter;
import ru.market.domain.data.CardAccount;
import ru.market.domain.repository.AbstractAccountRepository;
import ru.market.domain.service.ICardAccountService;
import ru.market.domain.service.IPersonProvider;

import ru.market.dto.card.CardAccountDTO;
import ru.market.dto.card.CardAccountNoIdDTO;

public class CardAccountServiceImpl extends AbstractAccountService<CardAccount, CardAccountNoIdDTO, CardAccountDTO>
        implements ICardAccountService {

    public CardAccountServiceImpl(AbstractAccountRepository<CardAccount> abstractAccountRepository,
                                  AbstractDefaultConverter<CardAccount, CardAccountNoIdDTO, CardAccountDTO> abstractDefaultConverter,
                                  IPersonProvider personProvider) {

        super(abstractAccountRepository, abstractDefaultConverter, personProvider);
    }
}
