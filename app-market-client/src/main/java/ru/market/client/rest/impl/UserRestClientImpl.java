package ru.market.client.rest.impl;

import com.fasterxml.jackson.core.type.TypeReference;

import ru.market.client.exception.HttpConnectionException;
import ru.market.client.exception.RestClientException;
import ru.market.client.http.HttpConnection;
import ru.market.client.http.HttpResponse;
import ru.market.client.http.impl.HttpRequestImpl;
import ru.market.client.http.impl.HttpRequestWithBodyImpl;
import ru.market.client.rest.UserRestClient;

import ru.market.dto.result.ResultDTO;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserPasswordDTO;
import ru.market.dto.user.UserUsernameDTO;

public class UserRestClientImpl extends AbstractRestClient implements UserRestClient {
    private HttpConnection httpConnection;

    public UserRestClientImpl(HttpConnection httpConnection) {
        this.httpConnection = httpConnection;
    }

    @Override
    public UserDTO registration(RegistrationDTO registrationDTO) throws RestClientException {
        try {
            TypeReference<UserDTO> typeReference = new TypeReference<UserDTO>() {};

            HttpResponse<UserDTO> httpResponse = httpConnection.put(
                    new HttpRequestWithBodyImpl<>(createUrl("/registration"), typeReference, registrationDTO)
            );

            checkResponse(httpResponse);

            return httpResponse.getResponseBody();

        } catch (HttpConnectionException e){
            throw new RestClientException(e.getMessage());
        }
    }

    @Override
    public UserDTO changeUsername(UserUsernameDTO usernameDTO) throws RestClientException {
        try {
            TypeReference<UserDTO> typeReference = new TypeReference<UserDTO>() {};

            HttpResponse<UserDTO> httpResponse = httpConnection.put(
                    new HttpRequestWithBodyImpl<>(createUrl("/change/username"), typeReference, usernameDTO)
            );

            checkResponse(httpResponse);

            return httpResponse.getResponseBody();

        } catch (HttpConnectionException e){
            throw new RestClientException(e.getMessage());
        }
    }

    @Override
    public ResultDTO changePassword(UserPasswordDTO userPasswordDTO) throws RestClientException {
        try {
            TypeReference<ResultDTO> typeReference = new TypeReference<ResultDTO>() {};

            HttpResponse<ResultDTO> httpResponse = httpConnection.put(
                    new HttpRequestWithBodyImpl<>(createUrl("/change/password"), typeReference, userPasswordDTO)
            );

            checkResponse(httpResponse);

            return httpResponse.getResponseBody();

        } catch (HttpConnectionException e){
            throw new RestClientException(e.getMessage());
        }
    }

    @Override
    public UserDTO getCurrent() throws RestClientException {
        try {
            TypeReference<UserDTO> typeReference = new TypeReference<UserDTO>() {};

            HttpResponse<UserDTO> httpResponse = httpConnection.get(
                    new HttpRequestImpl<>(createUrl("/user"), typeReference)
            );

            checkResponse(httpResponse);

            return httpResponse.getResponseBody();

        } catch (HttpConnectionException e){
            throw new RestClientException(e.getMessage());
        }

    }

    @Override
    public void deleteCurrent() throws RestClientException {
        try {
            TypeReference<Void> typeReference = new TypeReference<Void>() {};

            HttpResponse<Void> httpResponse = httpConnection.delete(
                    new HttpRequestImpl<>(createUrl("/user"), typeReference)
            );

            checkResponse(httpResponse);

        } catch (HttpConnectionException e){
            throw new RestClientException(e.getMessage());
        }
    }
}
