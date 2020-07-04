package ru.market.domain.validator.person;

import org.junit.Test;

import ru.market.domain.data.Person;
import ru.market.domain.exception.ValidateException;

public class PersonValidatorTest {
    private PersonValidator personValidator = new PersonValidator();

    @Test
    public void validateTest(){
        Person person = new Person();
        person.setFirstName("Фамилия");
        person.setLastName("Имя");
        person.setMiddleName("Отчество");

        personValidator.validate(person);
    }

    @Test(expected = ValidateException.class)
    public void invalidPersonFIOTest(){
        personValidator.validate(new Person());
    }
}