package ru.market.domain.utils;

import ru.market.domain.data.dto.UserDTO;

public final class DTOUtils {
    private DTOUtils(){}

    public static UserDTO createUserDTO(String firstName, String lastName, String middleName, String login){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setMiddleName(middleName);
        userDTO.setLogin(login);
        return userDTO;
    }
}
