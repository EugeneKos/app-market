package ru.market.domain.service.impl;

import ru.market.data.session.api.SessionDataManager;

import ru.market.domain.data.Person;
import ru.market.domain.service.IPersonProvider;
import ru.market.domain.service.IPersonService;

public class PersonProviderImpl implements IPersonProvider {
    private SessionDataManager sessionDataManager;
    private IPersonService personService;

    public PersonProviderImpl(SessionDataManager sessionDataManager, IPersonService personService) {
        this.sessionDataManager = sessionDataManager;
        this.personService = personService;
    }

    @Override
    public Person getCurrentPerson() {
        Long personId = sessionDataManager.getUserData().getPersonId();
        return personService.getPersonById(personId);
    }
}
