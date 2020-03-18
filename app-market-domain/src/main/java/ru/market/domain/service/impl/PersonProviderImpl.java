package ru.market.domain.service.impl;

import ru.market.data.session.api.PersonDataManagement;

import ru.market.domain.converter.PersonConverter;
import ru.market.domain.data.Person;
import ru.market.domain.service.IPersonProvider;
import ru.market.dto.person.PersonDTO;

public class PersonProviderImpl implements IPersonProvider {
    private PersonDataManagement personDataManagement;
    private PersonConverter personConverter;

    public PersonProviderImpl(PersonDataManagement personDataManagement, PersonConverter personConverter) {
        this.personDataManagement = personDataManagement;
        this.personConverter = personConverter;
    }

    @Override
    public Person getCurrentPerson() {
        PersonDTO personDTO = personDataManagement.getPerson();
        return personConverter.convertToPerson(personDTO);
    }
}
