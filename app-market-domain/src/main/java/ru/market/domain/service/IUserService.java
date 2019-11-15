package ru.market.domain.service;

import ru.market.domain.data.dto.UserDTO;

public interface IUserService {
    UserDTO create(UserDTO userDTO);
    UserDTO update(UserDTO userDTO);
    UserDTO getByLogin(String login);
}
