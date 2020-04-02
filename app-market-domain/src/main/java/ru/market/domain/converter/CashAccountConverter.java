package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;

import ru.market.domain.data.CashAccount;

import ru.market.dto.cash.CashAccountDTO;
import ru.market.dto.cash.CashAccountNoIdDTO;

public class CashAccountConverter extends AbstractDefaultConverter<CashAccount, CashAccountNoIdDTO, CashAccountDTO> {
    public CashAccountConverter(DozerBeanMapper mapper) {
        super(mapper, CashAccount.class, CashAccountNoIdDTO.class, CashAccountDTO.class);
    }
}
