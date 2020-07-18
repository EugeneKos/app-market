package ru.market.domain.validator.money;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.domain.data.MoneyAccount;
import ru.market.domain.data.Person;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.repository.MoneyAccountRepository;
import ru.market.domain.repository.PersonRepository;
import ru.market.domain.validator.CommonValidator;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:app-market-test.properties")
public class MoneyAccountValidatorTest {
    @Autowired
    private CommonValidator<MoneyAccount> moneyAccountValidator;

    @Autowired
    private MoneyAccountRepository moneyAccountRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void validateTest(){
        Person person = createMoneyAccountAndGetPerson("First", "Last", "Middle",
                "Money 1", "5000");

        MoneyAccount moneyAccount = new MoneyAccount();
        moneyAccount.setBalance(new BigDecimal("4000"));
        moneyAccount.setName("Test Money 1");
        moneyAccount.setPerson(person);

        moneyAccountValidator.validate(moneyAccount);
    }

    @Test(expected = ValidateException.class)
    public void notUniqueNameTest(){
        Person person = createMoneyAccountAndGetPerson("First1", "Last1", "Middle1",
                "Money 2", "7000");

        MoneyAccount moneyAccount = new MoneyAccount();
        moneyAccount.setBalance(new BigDecimal("4000"));
        moneyAccount.setName("Money 2");
        moneyAccount.setPerson(person);

        moneyAccountValidator.validate(moneyAccount);
    }

    @Test(expected = ValidateException.class)
    public void incorrectBalanceTest(){
        MoneyAccount moneyAccount = new MoneyAccount();
        moneyAccount.setBalance(new BigDecimal("4000.125"));

        moneyAccountValidator.validate(moneyAccount);
    }

    private Person createMoneyAccountAndGetPerson(String firstName, String lastName, String middleName,
                                                  String moneyName, String moneyBalance){

        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setMiddleName(middleName);

        person = personRepository.saveAndFlush(person);

        MoneyAccount created = new MoneyAccount();
        created.setBalance(new BigDecimal(moneyBalance));
        created.setName(moneyName);
        created.setDateCreated(LocalDate.now());
        created.setPerson(person);

        moneyAccountRepository.saveAndFlush(created);

        return person;
    }
}