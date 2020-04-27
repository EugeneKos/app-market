package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;

import ru.market.domain.data.Person;

import ru.market.dto.person.PersonDTO;
import ru.market.dto.person.PersonNoIdDTO;

public class PersonConverter extends AbstractDefaultConverter<Person, PersonNoIdDTO, PersonDTO> {
    public PersonConverter(DozerBeanMapper mapper) {
        super(mapper, Person.class, PersonNoIdDTO.class, PersonDTO.class);
    }
}
