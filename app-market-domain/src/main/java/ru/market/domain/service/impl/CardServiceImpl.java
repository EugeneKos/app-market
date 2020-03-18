package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.CardConverter;
import ru.market.domain.data.Card;
import ru.market.domain.exception.MustIdException;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.repository.CardRepository;
import ru.market.domain.service.ICardService;
import ru.market.domain.service.IPersonProvider;
import ru.market.dto.card.CardDTO;
import ru.market.dto.card.CardNoIdDTO;

import java.util.HashSet;
import java.util.Set;

public class CardServiceImpl implements ICardService {
    private CardRepository cardRepository;
    private CardConverter cardConverter;

    private IPersonProvider personProvider;

    public CardServiceImpl(CardRepository cardRepository, CardConverter cardConverter) {
        this.cardRepository = cardRepository;
        this.cardConverter = cardConverter;
    }

    public void setPersonProvider(IPersonProvider personProvider) {
        this.personProvider = personProvider;
    }

    @Transactional
    @Override
    public CardDTO create(CardNoIdDTO cardNoIdDTO) {
        return update(cardNoIdDTO, false);
    }

    @Transactional
    @Override
    public CardDTO update(CardDTO cardDTO) {
        return update(cardDTO, true);
    }

    private CardDTO update(CardNoIdDTO cardDTO, boolean isMustId){
        if(cardDTO == null){
            return null;
        }

        Card card = cardConverter.convertToCard(cardDTO);
        if(isMustId && card.getId() == null){
            throw new MustIdException("Card id should be given");
        }

        assertExistById(card);
        assertUniqueByNumber(card);

        card.setPerson(personProvider.getCurrentPerson());

        card = cardRepository.saveAndFlush(card);
        return cardConverter.convertToCardDTO(card);
    }

    private void assertExistById(Card card){
        if(card.getId() == null){
            return;
        }

        cardRepository.findById(card.getId()).orElseThrow(
                () -> new NotFoundException("Card with id " + card.getId() + " not found")
        );
    }

    private void assertUniqueByNumber(Card card){
        Card founded = cardRepository.findByNumber(card.getNumber());
        if(founded != null && !founded.getId().equals(card.getId())){
            throw new NotUniqueException("Card with number: " + founded.getNumber() + " already exist");
        }
    }

    @Override
    public CardDTO getById(Long id) {
        Card card = cardRepository.findById(id).orElse(null);
        return cardConverter.convertToCardDTO(card);
    }

    @Override
    public Set<CardDTO> getAll() {
        return new HashSet<>();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        cardRepository.deleteById(id);
    }
}
