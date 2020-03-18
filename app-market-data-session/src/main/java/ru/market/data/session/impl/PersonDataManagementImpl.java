package ru.market.data.session.impl;

import ru.market.data.session.api.PersonDataManagement;
import ru.market.dto.person.PersonDTO;

public class PersonDataManagementImpl implements PersonDataManagement {
    @Override
    public void setPerson(PersonDTO person) {
        SessionContext.setSessionAttribute(SessionAttributeNames.PERSON, person);
    }

    @Override
    public PersonDTO getPerson() {
        return SessionContext.getSessionAttribute(SessionAttributeNames.PERSON);
    }

    @Override
    public void removePerson() {
        SessionContext.removeSessionAttribute(SessionAttributeNames.PERSON);
    }
}
