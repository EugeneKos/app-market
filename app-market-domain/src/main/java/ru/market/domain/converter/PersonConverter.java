package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;

import ru.market.domain.data.Person;

import ru.market.dto.person.PersonDTO;
import ru.market.dto.person.PersonWithPasswordDTO;

public class PersonConverter extends AbstractDefaultConverter<Person, PersonDTO, PersonWithPasswordDTO> {
    public PersonConverter(DozerBeanMapper mapper) {
        super(mapper, Person.class, PersonDTO.class, PersonWithPasswordDTO.class);
    }
}
