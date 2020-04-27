package ru.market.domain.service;

import ru.market.dto.result.ResultDTO;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserPasswordDTO;
import ru.market.dto.user.UserSecretDTO;
import ru.market.dto.user.UserUsernameDTO;

public interface IUserService {
    UserDTO registration(RegistrationDTO registrationDTO);
    UserDTO changeUsername(UserUsernameDTO usernameDTO, Long userId);
    ResultDTO changePassword(UserPasswordDTO passwordDTO, Long userId);
    UserDTO getById(Long id);
    UserSecretDTO getByUsername(String username);
    void deleteById(Long id);
}