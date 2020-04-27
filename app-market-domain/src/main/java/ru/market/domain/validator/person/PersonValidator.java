package ru.market.domain.validator.person;

import org.springframework.util.StringUtils;

import ru.market.domain.data.Person;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.validator.CommonValidator;

public class PersonValidator implements CommonValidator<Person> {
    @Override
    public void validate(Person person) throws ValidateException {
        assertFIONotNull(person);
    }

    private void assertFIONotNull(Person person){
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        String middleName = person.getMiddleName();

        if(StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName) || StringUtils.isEmpty(middleName)){
            throw new ValidateException("Фамилия, имя и отчество должны быть заполнены!");
        }
    }
}
