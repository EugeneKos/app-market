package ru.market.domain.service;

import ru.market.dto.card.CardAccountDTO;
import ru.market.dto.card.CardAccountNoIdDTO;

public interface ICardAccountService extends DefaultAccountService<CardAccountNoIdDTO, CardAccountDTO> {
}
