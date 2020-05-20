package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;

import ru.market.domain.data.CardAccount;

import ru.market.dto.card.CardAccountDTO;
import ru.market.dto.card.CardAccountNoIdDTO;

public class CardAccountConverter extends AbstractAccountConverter<CardAccount, CardAccountNoIdDTO, CardAccountDTO> {
    public CardAccountConverter(DozerBeanMapper mapper) {
        super(mapper, CardAccount.class, CardAccountNoIdDTO.class, CardAccountDTO.class);
    }
}
