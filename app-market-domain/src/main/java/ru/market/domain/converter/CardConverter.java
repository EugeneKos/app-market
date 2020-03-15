package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import ru.market.domain.data.Card;
import ru.market.dto.card.CardDTO;
import ru.market.dto.card.CardNoIdDTO;

@Service
public class CardConverter {
    private DozerBeanMapper mapper;

    public CardConverter(DozerBeanMapper mapper) {
        this.mapper = mapper;
    }

    public Card convertToCard(CardNoIdDTO cardDTO){
        if(cardDTO == null){
            return null;
        }
        return mapper.map(cardDTO, Card.class);
    }

    public CardDTO convertToCardDTO(Card card){
        if(card == null){
            return null;
        }
        return mapper.map(card, CardDTO.class);
    }
}
