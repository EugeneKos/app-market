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
import ru.market.dto.person.PersonWithPasswordDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:data-test-config.properties")
public class IPersonServiceTest {
    @Autowired
    private IPersonService personService;

    @Test
    public void createTest(){
        PersonWithPasswordDTO personWithPasswordDTO = createPersonDTO("Иван", "Иванов",
                "Иванович", "ivanov", "ivanow123456");

        PersonDTO personDTO = personService.create(personWithPasswordDTO);

        Assert.assertNotNull(personDTO);
        Assert.assertNotNull(personDTO.getId());
    }

    @Test
    public void updateTest(){
        PersonWithPasswordDTO personWithPasswordDTO = createPersonDTO("Иван1", "Иванов1",
                "Иванович1", "ivanov1", "ivanow1234561");

        personWithPasswordDTO.setId(2556484L);

        PersonDTO personDTO = personService.create(personWithPasswordDTO);

        Assert.assertNotNull(personDTO);
        Assert.assertNotNull(personDTO.getId());
        Assert.assertNotEquals(2556484L, personDTO.getId().longValue());
        Assert.assertEquals("Иван1", personDTO.getFirstName());
        Assert.assertEquals("Иванов1", personDTO.getLastName());
        Assert.assertEquals("Иванович1", personDTO.getMiddleName());
        Assert.assertEquals("ivanov1", personDTO.getUsername());

        personWithPasswordDTO.setId(personDTO.getId());
        personWithPasswordDTO.setUsername("ivanov55");
        personDTO = personService.update(personWithPasswordDTO);

        Assert.assertNotNull(personDTO);
        Assert.assertEquals("ivanov55", personDTO.getUsername());
    }

    @Test
    public void getByIdTest(){
        PersonWithPasswordDTO personWithPasswordDTO = createPersonDTO("Иван2", "Иванов2",
                "Иванович2", "ivanov2", "ivanow1234562");

        PersonDTO personDTO = personService.create(personWithPasswordDTO);
        Assert.assertNotNull(personDTO);

        PersonDTO founded = personService.getById(personDTO.getId());
        Assert.assertNotNull(founded);
    }

    @Test
    public void deleteByIdTest(){
        PersonWithPasswordDTO personWithPasswordDTO = createPersonDTO("Иван3", "Иванов3",
                "Иванович3", "ivanov3", "ivanow123456");

        PersonDTO personDTO = personService.create(personWithPasswordDTO);
        Assert.assertNotNull(personDTO);

        personService.deleteById(personDTO.getId());

        personDTO = personService.getById(personDTO.getId());
        Assert.assertNull(personDTO);
    }

    private PersonWithPasswordDTO createPersonDTO(String firstName, String lastName, String middleName,
                                                  String username, String password){

        PersonWithPasswordDTO personWithPasswordDTO = new PersonWithPasswordDTO();
        personWithPasswordDTO.setFirstName(firstName);
        personWithPasswordDTO.setLastName(lastName);
        personWithPasswordDTO.setMiddleName(middleName);
        personWithPasswordDTO.setUsername(username);
        personWithPasswordDTO.setPassword(password);
        return personWithPasswordDTO;
    }
}