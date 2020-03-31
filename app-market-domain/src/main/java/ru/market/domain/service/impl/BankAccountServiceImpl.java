package ru.market.domain.service.impl;

import ru.market.domain.converter.BankAccountConverter;
import ru.market.domain.data.BankAccount;
import ru.market.domain.repository.BankAccountRepository;
import ru.market.domain.service.IBankAccountService;
import ru.market.domain.service.IPersonProvider;

import ru.market.dto.bank.BankAccountDTO;
import ru.market.dto.bank.BankAccountNoIdDTO;

public class BankAccountServiceImpl extends AbstractBankService<BankAccount, BankAccountNoIdDTO, BankAccountDTO>
        implements IBankAccountService {

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository,
                                  BankAccountConverter bankAccountConverter,
                                  IPersonProvider personProvider) {

        super(bankAccountRepository, bankAccountConverter, personProvider);
    }
}
