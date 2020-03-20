package ru.market.domain.service;

import ru.market.dto.cash.CashDTO;
import ru.market.dto.cash.CashNoIdDTO;

import java.util.Set;

public interface ICashService {
    CashDTO create(CashNoIdDTO cashNoIdDTO);
    CashDTO update(CashDTO cashDTO);
    CashDTO getById(Long id);
    Set<CashDTO> getAll();
    Set<Long> getAllCashIdByPersonId(Long personId);
    void deleteById(Long id);
    void deleteAllByPersonId(Long personId);
}