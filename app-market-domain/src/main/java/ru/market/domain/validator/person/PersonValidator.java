package ru.market.domain.validator.person;

import org.springframework.util.StringUtils;

import ru.market.domain.data.Person;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.repository.common.PersonRepository;
import ru.market.domain.validator.CommonValidator;

public class PersonValidator implements CommonValidator<Person> {
    private static final int REQUIRED_PASSWORD_LENGTH = 8;

    private PersonRepository personRepository;

    public PersonValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void validate(Person person) throws ValidateException {
        assertFIONotNull(person);
        validatePassword(person);

        try {
            assertExistById(person);
            assertUniqueByUsername(person);
        } catch (Exception e){
            throw new ValidateException("Ошибка валидации пользователя", e);
        }
    }

    private void assertFIONotNull(Person person){
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        String middleName = person.getMiddleName();

        if(StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName) || StringUtils.isEmpty(middleName)){
            throw new ValidateException("Фамилия, имя и отчество должны быть заполнены!");
        }
    }

    private void validatePassword(Person person){
        String password = person.getPassword();
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

    private void assertExistById(Person person){
        if(person.getId() == null){
            return;
        }

        personRepository.findById(person.getId()).orElseThrow(
                () -> new NotFoundException("Person with id " + person.getId() + " not found")
        );
    }

    private void assertUniqueByUsername(Person person){
        Person founded = personRepository.findByUsername(person.getUsername());
        if(founded != null && !founded.getId().equals(person.getId())){
            throw new NotUniqueException("Person with username: " + founded.getUsername() + " already exist");
        }
    }
}
