package ru.market.domain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ru.market.domain.converter.PersonConverter;
import ru.market.domain.converter.UserConverter;
import ru.market.domain.data.Person;
import ru.market.domain.data.User;
import ru.market.domain.data.enumeration.UserStatus;
import ru.market.domain.exception.BadRequestException;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.repository.UserRepository;
import ru.market.domain.service.IPersonService;
import ru.market.domain.service.IUserService;
import ru.market.domain.validator.CommonValidator;
import ru.market.domain.validator.user.UserValidator;
import ru.market.domain.validator.user.UserValidatorStrategy;

import ru.market.dto.person.PersonDTO;
import ru.market.dto.person.PersonNoIdDTO;
import ru.market.dto.result.ResultDTO;
import ru.market.dto.result.ResultStatus;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserPasswordDTO;
import ru.market.dto.user.UserAdditionalDTO;
import ru.market.dto.user.UserUsernameDTO;

import java.time.LocalDateTime;

public class UserServiceImpl implements IUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private UserConverter userConverter;

    private CommonValidator<User> validator;

    private IPersonService personService;
    private PersonConverter personConverter;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter,
                           IPersonService personService,
                           PersonConverter personConverter) {

        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.personService = personService;
        this.personConverter = personConverter;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDTO registration(RegistrationDTO registrationDTO) {
        LOGGER.info("Регистрация нового пользователя");
        if(registrationDTO == null){
            throw new BadRequestException("Данные для регистрации пользователя не заданы");
        }

        UserAdditionalDTO userAdditionalDTO = userConverter.convertToUserAdditionalDTO(registrationDTO);
        User user = userConverter.convertToEntity(userAdditionalDTO);

        validator = new UserValidator(userRepository, UserValidatorStrategy.FULL);
        validator.validate(user);

        PersonNoIdDTO personNoIdDTO = userConverter.convertToPersonNoIdDTO(registrationDTO);
        PersonDTO personDTO = personService.create(personNoIdDTO);
        Person person = personConverter.convertToEntity(personDTO);

        user.setPerson(person);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setTimestampStatus(LocalDateTime.now());
        user.setPasswordAttemptCount(0);
        LOGGER.debug("Установка числа попыток ввода пароля: 0 . Статус пользователя: ACTIVE");

        user = userRepository.saveAndFlush(user);
        return userConverter.convertToBasedDTO(user);
    }

    @Transactional
    @Override
    public UserDTO changeUsername(UserUsernameDTO usernameDTO, Long userId) {
        LOGGER.info("Изменение имени пользователя. UserId = {}", userId);
        if(usernameDTO == null){
            throw new BadRequestException("Данные для замены имени пользователя не заданы");
        }

        User user = getUserById(userId);
        user.setUsername(usernameDTO.getUsername());

        validator = new UserValidator(userRepository, UserValidatorStrategy.USERNAME_ONLY);
        validator.validate(user);

        user = userRepository.saveAndFlush(user);
        return userConverter.convertToBasedDTO(user);
    }

    @Transactional
    @Override
    public ResultDTO changePassword(UserPasswordDTO passwordDTO, Long userId) {
        LOGGER.info("Изменение пароля пользователя. UserId = {}", userId);
        if(passwordDTO == null || StringUtils.isEmpty(passwordDTO.getOldPassword())){
            throw new BadRequestException("Данные для замены пароля не заданы");
        }

        User user = getUserById(userId);

        boolean isPasswordMatches = passwordEncoder.matches(passwordDTO.getOldPassword(), user.getPassword());
        if(!isPasswordMatches){
            LOGGER.error("Пароли не совпадают, запрос на изменение пароля отклонен! UserId = {}", userId);
            return new ResultDTO(ResultStatus.FAILED, "Пароли не совпадают, запрос на изменение пароля отклонен!");
        }

        user.setPassword(passwordDTO.getNewPassword());

        validator = new UserValidator(userRepository, UserValidatorStrategy.PASSWORD_ONLY);
        validator.validate(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);

        LOGGER.info("Пароль был изменен. UserId = {}", userId);
        return new ResultDTO(ResultStatus.SUCCESS, "Пароль был изменен");
    }

    private User getUserById(Long id){
        LOGGER.info("Получение пользователя по id = {}", id);
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
    public UserAdditionalDTO getByUsername(String username) {
        LOGGER.info("Получение пользователя по username = {}", username);
        User user = userRepository.findByUsername(username);
        return userConverter.convertToDTO(user);
    }

    @Transactional
    @Override
    public void updateUserStatusByUsername(String username, UserStatus status) {
        LOGGER.info("Обновление статуса пользователя. Username = {} , status = {}", username, status);
        userRepository.updateUserStatusAndTimestampStatusByUsername(username, status, LocalDateTime.now());
    }

    @Transactional
    @Override
    public void updatePasswordAttemptCountByUsername(String username, Integer passwordAttemptCount) {
        LOGGER.info("Обновление числа попыток ввода пароля. Username = {}, passwordAttemptCount = {}", username, passwordAttemptCount);
        userRepository.updatePasswordAttemptCountByUsername(username, passwordAttemptCount);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        LOGGER.info("Удаление пользователя по id = {}", id);
        userRepository.deleteById(id);
    }
}
