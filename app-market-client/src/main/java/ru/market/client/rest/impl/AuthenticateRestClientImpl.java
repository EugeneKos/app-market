package ru.market.client.rest.impl;

import com.fasterxml.jackson.core.type.TypeReference;

import ru.market.client.exception.RestClientException;
import ru.market.client.http.HttpConnection;
import ru.market.client.http.HttpResponse;
import ru.market.client.http.impl.HttpRequestImpl;
import ru.market.client.http.impl.HttpRequestWithBodyImpl;
import ru.market.client.rest.AuthenticateRestClient;

import ru.market.dto.auth.UsernamePasswordDTO;
import ru.market.dto.result.ResultDTO;

public class AuthenticateRestClientImpl extends AbstractRestClient implements AuthenticateRestClient {
    private HttpConnection httpConnection;

    public AuthenticateRestClientImpl(HttpConnection httpConnection) {
        this.httpConnection = httpConnection;
    }

    @Override
    public ResultDTO authenticate(UsernamePasswordDTO usernamePasswordDTO) throws RestClientException {
        TypeReference<ResultDTO> typeReference = new TypeReference<ResultDTO>() {};

        HttpResponse<ResultDTO> httpResponse = httpConnection.post(
                new HttpRequestWithBodyImpl<>(createUrl("/auth"), typeReference, usernamePasswordDTO)
        );

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }

    @Override
    public void logout() throws RestClientException {
        TypeReference<Void> typeReference = new TypeReference<Void>() {};

        HttpResponse<Void> httpResponse = httpConnection.get(
                new HttpRequestImpl<>(createUrl("/logout"), typeReference)
        );

        checkResponse(httpResponse);
    }
}
