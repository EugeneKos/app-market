package ru.market.domain.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ru.market.domain.converter.PersonConverter;
import ru.market.domain.converter.UserConverter;
import ru.market.domain.data.Person;
import ru.market.domain.data.User;
import ru.market.domain.exception.BadRequestException;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.repository.common.UserRepository;
import ru.market.domain.service.IPersonService;
import ru.market.domain.service.IUserService;
import ru.market.domain.validator.CommonValidator;

import ru.market.dto.person.PersonDTO;
import ru.market.dto.person.PersonNoIdDTO;
import ru.market.dto.result.ResultDTO;
import ru.market.dto.result.ResultStatus;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserPasswordDTO;
import ru.market.dto.user.UserSecretDTO;
import ru.market.dto.user.UserUsernameDTO;

public class UserServiceImpl implements IUserService {
    private UserRepository userRepository;
    private UserConverter userConverter;

    private CommonValidator<User> validator;

    private IPersonService personService;
    private PersonConverter personConverter;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter,
                           CommonValidator<User> validator,
                           IPersonService personService,
                           PersonConverter personConverter) {

        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.validator = validator;
        this.personService = personService;
        this.personConverter = personConverter;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDTO registration(RegistrationDTO registrationDTO) {
        if(registrationDTO == null){
            return null;
        }

        UserSecretDTO secretDTO = userConverter.convertToUserSecretDTO(registrationDTO);
        User user = userConverter.convertToEntity(secretDTO);

        validator.validate(user);

        PersonNoIdDTO personNoIdDTO = userConverter.convertToPersonNoIdDTO(registrationDTO);
        PersonDTO personDTO = personService.create(personNoIdDTO);
        Person person = personConverter.convertToEntity(personDTO);

        user.setPerson(person);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepository.saveAndFlush(user);
        return userConverter.convertToBasedDTO(user);
    }

    @Transactional
    @Override
    public UserDTO changeUsername(UserUsernameDTO usernameDTO, Long userId) {
        if(usernameDTO == null){
            throw new BadRequestException("Форма замены имени пользователя пуста");
        }

        User user = getUserById(userId);
        user.setUsername(usernameDTO.getUsername());

        validator.validate(user);

        user = userRepository.saveAndFlush(user);
        return userConverter.convertToBasedDTO(user);
    }

    @Transactional
    @Override
    public ResultDTO changePassword(UserPasswordDTO passwordDTO, Long userId) {
        if(passwordDTO == null || StringUtils.isEmpty(passwordDTO.getOldPassword())){
            throw new BadRequestException("Форма замены пароля пуста");
        }

        User user = getUserById(userId);

        boolean isPasswordMatches = passwordEncoder.matches(passwordDTO.getOldPassword(), user.getPassword());
        if(!isPasswordMatches){
            return new ResultDTO(ResultStatus.FAILED, "Пароли не совпадают, запрос на изменение пароля отклонен!");
        }

        user.setPassword(passwordDTO.getNewPassword());

        validator.validate(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);

        return new ResultDTO(ResultStatus.SUCCESS, "Пароль был изменен");
    }

    private User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("User with id %d not found", id))
        );
    }

    @Override
    public UserDTO getById(Long id) {
        User user = getUserById(id);
        return userConverter.convertToBasedDTO(user);
    }

    @Override
    public UserSecretDTO getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return userConverter.convertToDTO(user);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
