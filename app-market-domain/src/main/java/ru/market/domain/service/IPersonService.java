package ru.market.domain.service;

import ru.market.domain.data.Person;

import ru.market.dto.person.PersonDTO;
import ru.market.dto.person.PersonNoIdDTO;

public interface IPersonService {
    PersonDTO create(PersonNoIdDTO personNoIdDTO);
    PersonDTO update(PersonDTO personDTO);
    PersonDTO getById(Long id);
    Person getPersonById(Long id);
}
