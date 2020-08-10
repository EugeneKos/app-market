package ru.market.domain.validator.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.domain.data.Person;
import ru.market.domain.data.User;
import ru.market.domain.data.enumeration.UserStatus;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.repository.PersonRepository;
import ru.market.domain.repository.UserRepository;

import java.time.LocalDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:app-market-test.properties")
public class UserValidatorTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void validateTest(){
        User user = createUser("username", "passwordPASS123");
        user = userRepository.saveAndFlush(user);

        UserValidator userValidator = new UserValidator(userRepository, UserValidatorStrategy.FULL);

        userValidator.validate(user);
    }

    @Test(expected = ValidateException.class)
    public void validateNotValidUsernameTest(){
        Person person = new Person();
        person.setFirstName("First");
        person.setLastName("Last");
        person.setMiddleName("Middle");

        person = personRepository.saveAndFlush(person);

        User created = createUser("username1", "passwordPASS123");
        created.setPerson(person);

        userRepository.saveAndFlush(created);

        User user = createUser("username1", "passwordPASS123");
        user.setId(100L);

        UserValidator userValidator = new UserValidator(userRepository, UserValidatorStrategy.USERNAME_ONLY);

        userValidator.validate(user);
    }

    @Test(expected = ValidateException.class)
    public void validateNotValidPasswordTest(){
        User user = createUser("username", "password");

        UserValidator userValidator = new UserValidator(userRepository, UserValidatorStrategy.PASSWORD_ONLY);

        userValidator.validate(user);
    }

    private User createUser(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setTimestampStatus(LocalDateTime.now());
        user.setStatus(UserStatus.ACTIVE);
        user.setPasswordAttemptCount(0);

        return user;
    }
}