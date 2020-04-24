package ru.market.domain.service;

import java.util.Set;

public interface DefaultAccountService<NoIdDTO, DTO extends NoIdDTO> {
    DTO create(NoIdDTO cardNoIdDTO);
    DTO update(DTO cardDTO);

    DTO getById(Long id);
    Set<DTO> getAll();
    Set<Long> getAllAccountIdByPersonId(Long personId);

    void deleteById(Long id);
}
