package ru.market.domain.service;

import ru.market.domain.data.MoneyAccount;

import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;

import java.util.Set;

public interface IMoneyAccountService {
    MoneyAccountDTO create(MoneyAccountNoIdDTO moneyAccountNoIdDTO);

    void update(MoneyAccount moneyAccount);

    MoneyAccount getMoneyAccountById(Long id);

    MoneyAccountDTO getById(Long id);
    Set<MoneyAccountDTO> getAll();
    Set<Long> getAllIdByPersonId(Long personId);

    void deleteById(Long id);
}
