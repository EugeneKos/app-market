package ru.market.client.rest.impl;

import com.fasterxml.jackson.core.type.TypeReference;

import ru.market.client.exception.HttpConnectionException;
import ru.market.client.exception.RestClientException;
import ru.market.client.http.HttpConnection;
import ru.market.client.http.HttpResponse;
import ru.market.client.http.impl.HttpRequestImpl;
import ru.market.client.http.impl.HttpRequestWithBodyImpl;
import ru.market.client.rest.PersonRestClient;

import ru.market.dto.person.PersonDTO;

public class PersonRestClientImpl extends AbstractRestClient implements PersonRestClient {
    private HttpConnection httpConnection;

    public PersonRestClientImpl(HttpConnection httpConnection) {
        this.httpConnection = httpConnection;
    }

    @Override
    public PersonDTO getCurrent() throws RestClientException {
        try {
            TypeReference<PersonDTO> typeReference = new TypeReference<PersonDTO>() {};

            HttpResponse<PersonDTO> httpResponse = httpConnection.get(
                    new HttpRequestImpl<>(createUrl("/person"), typeReference)
            );

            checkResponse(httpResponse);

            return httpResponse.getResponseBody();

        } catch (HttpConnectionException e){
            throw new RestClientException(e.getMessage());
        }
    }

    @Override
    public PersonDTO update(PersonDTO personDTO) throws RestClientException {
        try {
            TypeReference<PersonDTO> typeReference = new TypeReference<PersonDTO>() {};

            HttpResponse<PersonDTO> httpResponse = httpConnection.post(
                    new HttpRequestWithBodyImpl<>(createUrl("/person"), typeReference, personDTO)
            );

            checkResponse(httpResponse);

            return httpResponse.getResponseBody();

        } catch (HttpConnectionException e){
            throw new RestClientException(e.getMessage());
        }
    }
}
