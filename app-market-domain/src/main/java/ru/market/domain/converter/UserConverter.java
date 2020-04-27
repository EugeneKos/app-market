package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;

import ru.market.domain.data.User;

import ru.market.dto.person.PersonNoIdDTO;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserSecretDTO;

public class UserConverter extends AbstractDefaultConverter<User, UserDTO, UserSecretDTO> {
    private DozerBeanMapper mapper;

    public UserConverter(DozerBeanMapper mapper) {
        super(mapper, User.class, UserDTO.class, UserSecretDTO.class);
        this.mapper = mapper;
    }

    public UserSecretDTO convertToUserSecretDTO(RegistrationDTO registrationDTO){
        if(registrationDTO == null){
            return null;
        }

        return mapper.map(registrationDTO, UserSecretDTO.class);
    }

    public PersonNoIdDTO convertToPersonNoIdDTO(RegistrationDTO registrationDTO){
        if(registrationDTO == null){
            return null;
        }

        return mapper.map(registrationDTO, PersonNoIdDTO.class);
    }
}
