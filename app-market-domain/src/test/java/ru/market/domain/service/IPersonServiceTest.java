package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.domain.data.Person;
import ru.market.dto.person.PersonDTO;
import ru.market.dto.person.PersonNoIdDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:database-test-config.properties")
public class IPersonServiceTest {
    @Autowired
    private IPersonService personService;

    @Test
    public void createTest(){
        PersonNoIdDTO personNoIdDTO = createPersonDTO("Иванов", "Иван", "Иванович");

        System.out.println("---------- create ----------");
        PersonDTO personDTO = personService.create(personNoIdDTO);
        System.out.println("----------------------------");

        Assert.assertNotNull(personDTO);
        Assert.assertNotNull(personDTO.getId());
    }

    @Test
    public void getPersonByIdTest(){
        PersonNoIdDTO personNoIdDTO = createPersonDTO("Иванов1", "Иван1", "Иванович1");

        PersonDTO personDTO = personService.create(personNoIdDTO);
        Assert.assertNotNull(personDTO);

        System.out.println("---------- getPersonById ----------");
        Person founded = personService.getPersonById(personDTO.getId());
        System.out.println("-----------------------------------");
        Assert.assertNotNull(founded);
    }

    @Test
    public void updateTest(){
        PersonNoIdDTO personNoIdDTO = createPersonDTO("Иванов2", "Иван2", "Иванович2");

        PersonDTO personDTO = personService.create(personNoIdDTO);
        Assert.assertNotNull(personDTO);

        personDTO.setLastName("Петр");

        System.out.println("---------- update ----------");
        personDTO = personService.update(personDTO);
        System.out.println("----------------------------");
        Assert.assertNotNull(personDTO);
        Assert.assertEquals("Петр", personDTO.getLastName());
    }

    @Test
    public void getByIdTest(){
        PersonNoIdDTO personNoIdDTO = createPersonDTO("Иванов3", "Иван3", "Иванович3");

        PersonDTO personDTO = personService.create(personNoIdDTO);
        Assert.assertNotNull(personDTO);

        System.out.println("---------- getById ----------");
        PersonDTO founded = personService.getById(personDTO.getId());
        System.out.println("-----------------------------");
        Assert.assertNotNull(founded);
    }

    private PersonNoIdDTO createPersonDTO(String firstName, String lastName, String middleName){

        PersonNoIdDTO personNoIdDTO = new PersonNoIdDTO();
        personNoIdDTO.setFirstName(firstName);
        personNoIdDTO.setLastName(lastName);
        personNoIdDTO.setMiddleName(middleName);
        return personNoIdDTO;
    }
}