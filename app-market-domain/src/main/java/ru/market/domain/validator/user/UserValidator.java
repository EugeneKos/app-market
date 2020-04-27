package ru.market.domain.validator.user;

import org.springframework.util.StringUtils;

import ru.market.domain.data.User;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.repository.common.UserRepository;
import ru.market.domain.validator.CommonValidator;

public class UserValidator implements CommonValidator<User> {
    private static final int REQUIRED_PASSWORD_LENGTH = 8;

    private UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validate(User user) throws ValidateException {
        validatePassword(user);
        validateUsername(user);
    }

    private void validatePassword(User user){
        String password = user.getPassword();
        if(StringUtils.isEmpty(password)){
            throw new ValidateException("Пароль должен быть задан!");
        }

        if(password.length() < REQUIRED_PASSWORD_LENGTH){
            throw new ValidateException(String.format("Пароль должен быть не меньше %d символов", REQUIRED_PASSWORD_LENGTH));
        }

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

        if(isUppercasePresent && isLowercasePresent && isDigitalPresent){
            return;
        }

        throw new ValidateException("Пароль должен содержать цифры, заглавные и строчные буквы латинского алфавита");
    }

    private void validateUsername(User user){
        String username = user.getUsername();

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
    }

    private void assertUniqueByUsername(User user){
        User founded = userRepository.findByUsername(user.getUsername());
        if(founded != null && !founded.getId().equals(user.getId())){
            throw new NotUniqueException("Person with username: " + founded.getUsername() + " already exist");
        }
    }
}
