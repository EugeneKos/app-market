package ru.market.domain.service.impl;

import ru.market.domain.converter.AbstractDefaultConverter;
import ru.market.domain.data.CardAccount;
import ru.market.domain.repository.account.AccountRepository;
import ru.market.domain.repository.account.CardAccountRepository;
import ru.market.domain.service.ICardAccountService;
import ru.market.domain.service.IPersonProvider;

import ru.market.domain.validator.CommonValidator;
import ru.market.domain.validator.account.card.CardAccountValidator;
import ru.market.domain.validator.account.card.CardAccountValidatorImpl;

import ru.market.dto.card.CardAccountDTO;
import ru.market.dto.card.CardAccountNoIdDTO;

public class CardAccountServiceImpl extends AbstractAccountService<CardAccount, CardAccountNoIdDTO, CardAccountDTO>
        implements ICardAccountService {

    private CardAccountRepository cardAccountRepository;

    public CardAccountServiceImpl(AccountRepository<CardAccount> accountRepository,
                                  AbstractDefaultConverter<CardAccount, CardAccountNoIdDTO, CardAccountDTO> abstractDefaultConverter,
                                  IPersonProvider personProvider) {

        super(accountRepository, abstractDefaultConverter, personProvider);

        this.cardAccountRepository = (CardAccountRepository) accountRepository;
    }

    @Override
    protected CommonValidator<CardAccount> validator() {
        return entity -> {
            CardAccountValidator<CardAccount> cardAccountValidator = new CardAccountValidatorImpl<>(cardAccountRepository);
            cardAccountValidator.validateBalance(entity);
            cardAccountValidator.validateNumber(entity);
            cardAccountValidator.validateUniqueNumber(entity);
        };
    }
}
