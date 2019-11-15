package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.domain.data.User;
import ru.market.domain.data.dto.UserDTO;

@Service
public class UserConverter {
    private DozerBeanMapper mapper;

    @Autowired
    public UserConverter(DozerBeanMapper mapper) {
        this.mapper = mapper;
    }

    public User convertToUser(UserDTO userDTO){
        if(userDTO == null){
            return null;
        }
        return mapper.map(userDTO, User.class);
    }

    public UserDTO convertToUserDTO(User user){
        if(user == null){
            return null;
        }
        return mapper.map(user, UserDTO.class);
    }
}
