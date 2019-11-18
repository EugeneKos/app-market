package ru.market.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.UserConverter;
import ru.market.domain.data.User;
import ru.market.domain.data.dto.UserDTO;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.repository.UserRepository;
import ru.market.domain.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
    private UserRepository userRepository;
    private UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) {
        return update(userDTO);
    }

    @Override
    @Transactional
    public UserDTO update(UserDTO userDTO) {
        if(userDTO == null){
            return null;
        }

        User user = userConverter.convertToUser(userDTO);

        assertExistById(user);
        assertUniqueByLogin(user);

        user = userRepository.saveAndFlush(user);
        return userConverter.convertToUserDTO(user);
    }

    private void assertExistById(User user){
        if(user.getId() == null){
            return;
        }

        userRepository.findById(user.getId()).orElseThrow(
                () -> new NotFoundException("User with id " + user.getId() + " not found")
        );
    }

    private void assertUniqueByLogin(User user){
        User founded = userRepository.findByLogin(user.getLogin());
        if(founded != null && !founded.getId().equals(user.getId())){
            throw new NotUniqueException("User with login: " + founded.getLogin() + " already exist");
        }
    }

    @Override
    @Transactional
    public UserDTO getByLogin(String login) {
        User user = userRepository.findByLogin(login);
        return userConverter.convertToUserDTO(user);
    }
}
