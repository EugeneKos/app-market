package ru.market.domain.converter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.ConverterConfiguration;
import ru.market.domain.data.Person;
import ru.market.dto.person.PersonDTO;
import ru.market.dto.person.PersonWithPasswordDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class PersonConverterTest {
    @Autowired
    private PersonConverter personConverter;

    @Test
    public void convertToPersonTest(){
        PersonWithPasswordDTO personDTO = new PersonWithPasswordDTO();
        personDTO.setFirstName("Иван");
        personDTO.setLastName("Иванов");
        personDTO.setMiddleName("Иванович");
        personDTO.setUsername("ivanov");
        personDTO.setPassword("ivanow123456");

        Person person = personConverter.convertToEntity(personDTO);

        Assert.assertNotNull(person);
        Assert.assertEquals("Иван", person.getFirstName());
        Assert.assertEquals("Иванов", person.getLastName());
        Assert.assertEquals("Иванович", person.getMiddleName());
        Assert.assertEquals("ivanov", person.getUsername());
        Assert.assertEquals("ivanow123456", person.getPassword());
    }

    @Test
    public void convertToPersonDTOTest(){
        Person person = new Person();
        person.setFirstName("Иван");
        person.setLastName("Иванов");
        person.setMiddleName("Иванович");
        person.setUsername("ivanov");
        person.setPassword("ivanow123456");

        PersonDTO personDTO = personConverter.convertToBasedDTO(person);

        Assert.assertNotNull(personDTO);
        Assert.assertEquals("Иван", personDTO.getFirstName());
        Assert.assertEquals("Иванов", personDTO.getLastName());
        Assert.assertEquals("Иванович", personDTO.getMiddleName());
        Assert.assertEquals("ivanov", personDTO.getUsername());
    }
}