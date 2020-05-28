package ru.market.client.rest;

import ru.market.client.exception.RestClientException;

import ru.market.dto.result.ResultDTO;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserPasswordDTO;
import ru.market.dto.user.UserUsernameDTO;

public interface UserRestClient {
    UserDTO registration(RegistrationDTO registrationDTO) throws RestClientException;
    UserDTO changeUsername(UserUsernameDTO usernameDTO) throws RestClientException;
    ResultDTO changePassword(UserPasswordDTO userPasswordDTO) throws RestClientException;
    UserDTO getCurrent() throws RestClientException;
    void deleteCurrent() throws RestClientException;
}
