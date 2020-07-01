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
import ru.market.dto.person.PersonNoIdDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class PersonConverterTest {
    @Autowired
    private PersonConverter personConverter;

    @Test
    public void convertToBasedDTOTest(){
        Person person = new Person();
        person.setFirstName("Иван");
        person.setLastName("Иванов");
        person.setMiddleName("Иванович");

        PersonNoIdDTO personNoIdDTO = personConverter.convertToBasedDTO(person);

        Assert.assertNotNull(personNoIdDTO);
        Assert.assertEquals("Иван", personNoIdDTO.getFirstName());
        Assert.assertEquals("Иванов", personNoIdDTO.getLastName());
        Assert.assertEquals("Иванович", personNoIdDTO.getMiddleName());
    }

    @Test
    public void convertToEntityTest(){
        PersonNoIdDTO personDTO = new PersonNoIdDTO();
        personDTO.setFirstName("Иван");
        personDTO.setLastName("Иванов");
        personDTO.setMiddleName("Иванович");

        Person person = personConverter.convertToEntity(personDTO);

        Assert.assertNotNull(person);
        Assert.assertEquals("Иван", person.getFirstName());
        Assert.assertEquals("Иванов", person.getLastName());
        Assert.assertEquals("Иванович", person.getMiddleName());
    }

    @Test
    public void convertToDTOTest(){
        Person person = new Person();
        person.setFirstName("Иван");
        person.setLastName("Иванов");
        person.setMiddleName("Иванович");

        PersonDTO personDTO = personConverter.convertToDTO(person);

        Assert.assertNotNull(personDTO);
        Assert.assertEquals("Иван", personDTO.getFirstName());
        Assert.assertEquals("Иванов", personDTO.getLastName());
        Assert.assertEquals("Иванович", personDTO.getMiddleName());
    }
}