package ru.market.domain.service.impl;

import ru.market.domain.converter.AbstractDefaultConverter;
import ru.market.domain.data.BankAccount;
import ru.market.domain.repository.AbstractAccountRepository;
import ru.market.domain.service.IBankAccountService;
import ru.market.domain.service.IPersonProvider;

import ru.market.dto.bank.BankAccountDTO;
import ru.market.dto.bank.BankAccountNoIdDTO;

public class BankAccountServiceImpl extends AbstractAccountService<BankAccount, BankAccountNoIdDTO, BankAccountDTO>
        implements IBankAccountService {

    public BankAccountServiceImpl(AbstractAccountRepository<BankAccount> abstractAccountRepository,
                                  AbstractDefaultConverter<BankAccount, BankAccountNoIdDTO, BankAccountDTO> abstractDefaultConverter,
                                  IPersonProvider personProvider) {

        super(abstractAccountRepository, abstractDefaultConverter, personProvider);
    }
}
