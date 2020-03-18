package ru.market.data.session.api;

import ru.market.dto.person.PersonDTO;

public interface PersonDataManagement {
    void setPerson(PersonDTO person);
    PersonDTO getPerson();
    void removePerson();
}
