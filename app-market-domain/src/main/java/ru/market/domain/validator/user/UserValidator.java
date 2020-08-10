package ru.market.domain.validator.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import ru.market.domain.data.User;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.repository.UserRepository;
import ru.market.domain.validator.CommonValidator;

public class UserValidator implements CommonValidator<User> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserValidator.class);

    private static final int REQUIRED_PASSWORD_LENGTH = 8;

    private UserRepository userRepository;
    private UserValidatorStrategy strategy;

    public UserValidator(UserRepository userRepository, UserValidatorStrategy strategy) {
        this.userRepository = userRepository;
        this.strategy = strategy;
    }

    @Override
    public void validate(User user) throws ValidateException {
        LOGGER.info("Валидация пользовательских данных. Стратегия валидации: {}", strategy);
        switch (strategy){
            case FULL: {
                validatePassword(user);
                validateUsername(user);
                break;
            }
            case USERNAME_ONLY: {
                validateUsername(user);
                break;
            }
            case PASSWORD_ONLY: {
                validatePassword(user);
                break;
            }
            default:
                throw new IllegalArgumentException("Стратегия валидации учетных данных не определена");
        }
    }

    private void validatePassword(User user){
        LOGGER.info("Валидация пароля");
        String password = user.getPassword();
        if(StringUtils.isEmpty(password)){
            throw new ValidateException("Пароль должен быть задан!");
        }

        if(password.length() < REQUIRED_PASSWORD_LENGTH){
            throw new ValidateException(String.format("Пароль должен быть не меньше %d символов", REQUIRED_PASSWORD_LENGTH));
        }
        LOGGER.debug("Пароль подходит по длине");

        char[] passwordArray = password.toCharArray();

        boolean isDigitalPresent = false;
        boolean isLowercasePresent = false;
        boolean isUppercasePresent = false;

        for (char passwordChar : passwordArray){
            if(String.valueOf(passwordChar).matches("[A-Z]")){
                isUppercasePresent = true;
            }
            else if(String.valueOf(passwordChar).matches("[a-z]")){
                isLowercasePresent = true;
            }
            else if(String.valueOf(passwordChar).matches("\\d")){
                isDigitalPresent = true;
            }
        }

        LOGGER.debug("Пароль содержит буквы верхнего регистра: [{}]. " +
                "Пароль содержит буквы нижнего регистра: [{}]. " +
                "Пароль содержит цифры: [{}]", isUppercasePresent, isLowercasePresent, isDigitalPresent);

        if(isUppercasePresent && isLowercasePresent && isDigitalPresent){
            LOGGER.info("Валидация пароля прошла успешно");
            return;
        }

        throw new ValidateException("Пароль должен содержать цифры, заглавные и строчные буквы латинского алфавита");
    }

    private void validateUsername(User user){
        LOGGER.info("Валидация имени пользователя");
        String username = user.getUsername();
        LOGGER.debug("User [validate username] username = {}", username);

        if(StringUtils.isEmpty(username)){
            throw new ValidateException("Имя пользователя должно быть задано");
        }
        if(!username.matches("[A-Za-z0-9]+")){
            throw new ValidateException(
                    "Имя пользователя может состоять только из цифр, заглавных и строчных букв латинского алфавита"
            );
        }

        try {
            assertUniqueByUsername(user);
        } catch (Exception e){
            throw new ValidateException("Ошибка валидации пользователя", e);
        }
        LOGGER.info("Валидация имени пользователя прошла успешно");
    }

    private void assertUniqueByUsername(User user){
        User founded = userRepository.findByUsername(user.getUsername());
        if(founded != null && !founded.getId().equals(user.getId())){
            throw new NotUniqueException("Person with username: " + founded.getUsername() + " already exist");
        }
    }
}
