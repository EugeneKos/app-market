package ru.market.domain.service.impl;

import ru.market.domain.converter.AbstractDefaultConverter;
import ru.market.domain.data.CashAccount;
import ru.market.domain.repository.AbstractAccountRepository;
import ru.market.domain.service.ICashAccountService;
import ru.market.domain.service.IPersonProvider;

import ru.market.dto.cash.CashAccountDTO;
import ru.market.dto.cash.CashAccountNoIdDTO;

public class CashAccountServiceImpl extends AbstractAccountService<CashAccount, CashAccountNoIdDTO, CashAccountDTO>
        implements ICashAccountService {

    public CashAccountServiceImpl(AbstractAccountRepository<CashAccount> abstractAccountRepository,
                                  AbstractDefaultConverter<CashAccount, CashAccountNoIdDTO, CashAccountDTO> abstractDefaultConverter,
                                  IPersonProvider personProvider) {

        super(abstractAccountRepository, abstractDefaultConverter, personProvider);
    }
}
