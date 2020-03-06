package ru.market.domain.service;

import ru.market.dto.person.PersonDTO;

public interface IPersonService {
    PersonDTO create(PersonDTO personDTO);
    PersonDTO update(PersonDTO personDTO);
    PersonDTO getById(Long id);
    PersonDTO getByUsername(String username);
    void deleteById(Long id);
    void deleteByUsername(String username);
}
