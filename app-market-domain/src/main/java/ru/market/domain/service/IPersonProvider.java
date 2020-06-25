package ru.market.domain.service;

import ru.market.domain.data.Person;

public interface IPersonProvider {
    Person getCurrentPerson();
    Long getCurrentPersonId();
}
