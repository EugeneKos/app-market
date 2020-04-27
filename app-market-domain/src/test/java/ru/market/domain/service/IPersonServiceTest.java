package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.dto.person.PersonDTO;
import ru.market.dto.person.PersonNoIdDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:data-test-config.properties")
public class IPersonServiceTest {
    @Autowired
    private IPersonService personService;

    @Test
    public void createTest(){
        PersonNoIdDTO personNoIdDTO = createPersonDTO("Иван", "Иванов", "Иванович");

        PersonDTO personDTO = personService.create(personNoIdDTO);

        Assert.assertNotNull(personDTO);
        Assert.assertNotNull(personDTO.getId());
    }

    @Test
    public void getByIdTest(){
        PersonNoIdDTO personNoIdDTO = createPersonDTO("Иван2", "Иванов2", "Иванович2");

        PersonDTO personDTO = personService.create(personNoIdDTO);
        Assert.assertNotNull(personDTO);

        PersonDTO founded = personService.getById(personDTO.getId());
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