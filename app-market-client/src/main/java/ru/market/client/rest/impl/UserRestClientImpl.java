package ru.market.client.rest.impl;

import com.fasterxml.jackson.core.type.TypeReference;

import ru.market.client.exception.RestClientException;
import ru.market.client.http.HttpConnection;
import ru.market.client.http.HttpResponse;
import ru.market.client.http.impl.HttpRequestImpl;
import ru.market.client.http.impl.HttpRequestWithBodyImpl;
import ru.market.client.rest.UserRestClient;
import ru.market.client.url.UrlProvider;

import ru.market.dto.result.ResultDTO;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserPasswordDTO;
import ru.market.dto.user.UserUsernameDTO;

public class UserRestClientImpl extends AbstractRestClient implements UserRestClient {
    private HttpConnection httpConnection;

    public UserRestClientImpl(HttpConnection httpConnection, UrlProvider urlProvider) {
        super(urlProvider);
        this.httpConnection = httpConnection;
    }

    @Override
    public UserDTO registration(RegistrationDTO registrationDTO) throws RestClientException {
        TypeReference<UserDTO> typeReference = new TypeReference<UserDTO>() {};

        HttpResponse<UserDTO> httpResponse = httpConnection.put(
                new HttpRequestWithBodyImpl<>(createUrl("/registration"), typeReference, registrationDTO)
        );

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }

    @Override
    public UserDTO changeUsername(UserUsernameDTO usernameDTO) throws RestClientException {
        TypeReference<UserDTO> typeReference = new TypeReference<UserDTO>() {};

        HttpResponse<UserDTO> httpResponse = httpConnection.post(
                new HttpRequestWithBodyImpl<>(createUrl("/change/username"), typeReference, usernameDTO)
        );

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }

    @Override
    public ResultDTO changePassword(UserPasswordDTO userPasswordDTO) throws RestClientException {
        TypeReference<ResultDTO> typeReference = new TypeReference<ResultDTO>() {};

        HttpResponse<ResultDTO> httpResponse = httpConnection.post(
                new HttpRequestWithBodyImpl<>(createUrl("/change/password"), typeReference, userPasswordDTO)
        );

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }

    @Override
    public UserDTO getCurrent() throws RestClientException {
        TypeReference<UserDTO> typeReference = new TypeReference<UserDTO>() {};

        HttpResponse<UserDTO> httpResponse = httpConnection.get(
                new HttpRequestImpl<>(createUrl("/user"), typeReference)
        );

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }

    @Override
    public void deleteCurrent() throws RestClientException {
        TypeReference<Void> typeReference = new TypeReference<Void>() {};

        HttpResponse<Void> httpResponse = httpConnection.delete(
                new HttpRequestImpl<>(createUrl("/user"), typeReference)
        );

        checkResponse(httpResponse);
    }
}
