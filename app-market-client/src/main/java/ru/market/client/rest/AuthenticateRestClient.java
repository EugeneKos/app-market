package ru.market.client.rest;

import ru.market.client.exception.RestClientException;

import ru.market.dto.auth.UsernamePasswordDTO;
import ru.market.dto.result.ResultDTO;

public interface AuthenticateRestClient {
    ResultDTO authenticate(UsernamePasswordDTO usernamePasswordDTO) throws RestClientException;
    void logout() throws RestClientException;
}
