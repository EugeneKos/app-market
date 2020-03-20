package ru.market.domain.service;

import ru.market.dto.card.CardDTO;
import ru.market.dto.card.CardNoIdDTO;

import java.util.Set;

public interface ICardService {
    CardDTO create(CardNoIdDTO cardNoIdDTO);
    CardDTO update(CardDTO cardDTO);
    CardDTO getById(Long id);
    Set<CardDTO> getAll();
    Set<Long> getAllCardIdByPersonId(Long personId);
    void deleteById(Long id);
    void deleteAllByPersonId(Long personId);
}
