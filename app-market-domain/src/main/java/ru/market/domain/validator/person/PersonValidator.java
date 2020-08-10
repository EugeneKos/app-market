package ru.market.domain.validator.person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import ru.market.domain.data.Person;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.validator.CommonValidator;

public class PersonValidator implements CommonValidator<Person> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonValidator.class);

    @Override
    public void validate(Person person) throws ValidateException {
        assertFIONotNull(person);
    }

    private void assertFIONotNull(Person person){
        LOGGER.info("Валидация ФИО пользователя");
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        String middleName = person.getMiddleName();
        LOGGER.debug("Person [validate FIO] firstName = {}, lastName = {}, middleName = {}", firstName, lastName, middleName);

        if(StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName) || StringUtils.isEmpty(middleName)){
            throw new ValidateException("Фамилия, имя и отчество должны быть заполнены!");
        }
        LOGGER.info("Валидация ФИО пользователя прошла успешно");
    }
}
