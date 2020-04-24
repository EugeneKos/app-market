package ru.market.domain.service;

import ru.market.domain.data.BankAccount;

import ru.market.dto.bank.BankAccountDTO;
import ru.market.dto.bank.BankAccountNoIdDTO;

public interface IBankAccountService extends DefaultAccountService<BankAccountNoIdDTO, BankAccountDTO> {
    void save(BankAccount bankAccount);
    BankAccount getAccount(Long id);
    void deleteAllByPersonId(Long personId);
}
