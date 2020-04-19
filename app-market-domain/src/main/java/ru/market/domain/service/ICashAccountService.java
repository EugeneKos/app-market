package ru.market.domain.service;

import ru.market.dto.cash.CashAccountDTO;
import ru.market.dto.cash.CashAccountNoIdDTO;

public interface ICashAccountService extends DefaultAccountService<CashAccountNoIdDTO, CashAccountDTO> {
}
