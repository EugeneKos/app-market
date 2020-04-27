package ru.market.domain.service.impl;

import ru.market.data.session.api.UserDataManager;

import ru.market.domain.data.Person;
import ru.market.domain.service.IPersonProvider;
import ru.market.domain.service.IPersonService;

public class PersonProviderImpl implements IPersonProvider {
    private UserDataManager userDataManager;
    private IPersonService personService;

    public PersonProviderImpl(UserDataManager userDataManager, IPersonService personService) {
        this.userDataManager = userDataManager;
        this.personService = personService;
    }

    @Override
    public Person getCurrentPerson() {
        Long personId = userDataManager.getUserData().getPersonId();
        return personService.getPersonById(personId);
    }
}
