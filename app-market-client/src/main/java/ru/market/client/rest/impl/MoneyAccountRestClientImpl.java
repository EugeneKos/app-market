package ru.market.client.rest.impl;

import com.fasterxml.jackson.core.type.TypeReference;

import ru.market.client.exception.HttpConnectionException;
import ru.market.client.exception.RestClientException;
import ru.market.client.http.HttpConnection;
import ru.market.client.http.HttpResponse;
import ru.market.client.http.impl.HttpRequestImpl;
import ru.market.client.http.impl.HttpRequestWithBodyImpl;
import ru.market.client.rest.MoneyAccountRestClient;

import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;

import java.util.Set;

public class MoneyAccountRestClientImpl extends AbstractRestClient implements MoneyAccountRestClient {
    private HttpConnection httpConnection;

    public MoneyAccountRestClientImpl(HttpConnection httpConnection) {
        this.httpConnection = httpConnection;
    }

    @Override
    public MoneyAccountDTO create(MoneyAccountNoIdDTO moneyAccountNoIdDTO) throws RestClientException {
        try {
            TypeReference<MoneyAccountDTO> typeReference = new TypeReference<MoneyAccountDTO>() {};

            HttpResponse<MoneyAccountDTO> httpResponse = httpConnection.put(
                    new HttpRequestWithBodyImpl<>(createUrl("/money-account"), typeReference, moneyAccountNoIdDTO)
            );

            checkResponse(httpResponse);

            return httpResponse.getResponseBody();

        } catch (HttpConnectionException e) {
            throw new RestClientException(e.getMessage());
        }
    }

    @Override
    public MoneyAccountDTO getById(Long id) throws RestClientException {
        try {
            TypeReference<MoneyAccountDTO> typeReference = new TypeReference<MoneyAccountDTO>() {};

            HttpResponse<MoneyAccountDTO> httpResponse = httpConnection.get(
                    new HttpRequestImpl<>(createUrl("/money-account/" + id), typeReference)
            );

            checkResponse(httpResponse);

            return httpResponse.getResponseBody();

        } catch (HttpConnectionException e) {
            throw new RestClientException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws RestClientException {
        try {
            TypeReference<Void> typeReference = new TypeReference<Void>() {};

            HttpResponse<Void> httpResponse = httpConnection.delete(
                    new HttpRequestImpl<>(createUrl("/money-account/" + id), typeReference)
            );

            checkResponse(httpResponse);

        } catch (HttpConnectionException e) {
            throw new RestClientException(e.getMessage());
        }
    }

    @Override
    public Set<MoneyAccountDTO> getAll() throws RestClientException {
        try {
            TypeReference<Set<MoneyAccountDTO>> typeReference = new TypeReference<Set<MoneyAccountDTO>>() {};

            HttpResponse<Set<MoneyAccountDTO>> httpResponse = httpConnection.get(
                    new HttpRequestImpl<>(createUrl("/money-account"), typeReference)
            );

            checkResponse(httpResponse);

            return httpResponse.getResponseBody();

        } catch (HttpConnectionException e) {
            throw new RestClientException(e.getMessage());
        }
    }
}
