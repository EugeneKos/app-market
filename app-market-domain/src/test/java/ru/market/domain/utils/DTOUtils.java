package ru.market.domain.utils;

import ru.market.domain.data.dto.UserDTO;
import ru.market.domain.data.dto.UserWithPasswordDTO;

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

    public static UserWithPasswordDTO createUserWithPasswordDTO(String firstName, String lastName, String middleName,
                                                    String login, String password){

        UserWithPasswordDTO userWithPasswordDTO = new UserWithPasswordDTO();
        userWithPasswordDTO.setFirstName(firstName);
        userWithPasswordDTO.setLastName(lastName);
        userWithPasswordDTO.setMiddleName(middleName);
        userWithPasswordDTO.setLogin(login);
        userWithPasswordDTO.setPassword(password);
        return userWithPasswordDTO;
    }
}
