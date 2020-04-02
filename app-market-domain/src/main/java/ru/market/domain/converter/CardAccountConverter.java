package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;

import ru.market.domain.data.CardAccount;

import ru.market.dto.card.CardAccountDTO;
import ru.market.dto.card.CardAccountNoIdDTO;

public class CardAccountConverter extends AbstractAccountConverter<CardAccount, CardAccountNoIdDTO, CardAccountDTO> {
    private DozerBeanMapper mapper;

    public CardAccountConverter(DozerBeanMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CardAccountDTO convertToDTO(CardAccount cardAccount) {
        if(cardAccount == null){
            return null;
        }
        return mapper.map(cardAccount, CardAccountDTO.class);
    }

    @Override
    public CardAccount convertToEntity(CardAccountNoIdDTO cardAccountNoIdDTO) {
        if(cardAccountNoIdDTO == null){
            return null;
        }
        return mapper.map(cardAccountNoIdDTO, CardAccount.class);
    }
}
