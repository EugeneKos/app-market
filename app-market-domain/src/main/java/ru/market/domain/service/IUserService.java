package ru.market.domain.service;

import ru.market.domain.data.enumeration.UserStatus;

import ru.market.dto.result.ResultDTO;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserPasswordDTO;
import ru.market.dto.user.UserAdditionalDTO;
import ru.market.dto.user.UserUsernameDTO;

public interface IUserService {
    UserDTO registration(RegistrationDTO registrationDTO);
    UserDTO changeUsername(UserUsernameDTO usernameDTO, Long userId);
    ResultDTO changePassword(UserPasswordDTO passwordDTO, Long userId);
    UserDTO getById(Long id);
    UserAdditionalDTO getByUsername(String username);
    void updateUserStatusById(Long id, UserStatus status);
    void deleteById(Long id);
}
