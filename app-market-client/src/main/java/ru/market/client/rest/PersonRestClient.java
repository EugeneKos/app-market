package ru.market.client.rest;

import ru.market.client.exception.RestClientException;

import ru.market.dto.person.PersonDTO;

public interface PersonRestClient {
    PersonDTO getCurrent() throws RestClientException;
    PersonDTO update(PersonDTO personDTO) throws RestClientException;
}
