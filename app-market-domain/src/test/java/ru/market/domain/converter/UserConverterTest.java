package ru.market.domain.converter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.ConverterConfiguration;
import ru.market.domain.data.Person;
import ru.market.domain.data.User;
import ru.market.domain.data.enumeration.UserStatus;
import ru.market.dto.person.PersonDTO;
import ru.market.dto.person.PersonNoIdDTO;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserAdditionalDTO;
import ru.market.dto.user.UserDTO;

import java.time.LocalDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class UserConverterTest {
    @Autowired
    private UserConverter userConverter;

    @Test
    public void convertToBasedDTOTest(){
        Person person = new Person();
        person.setId(2L);
        person.setFirstName("Фамилия");
        person.setLastName("Имя");
        person.setMiddleName("Отчество");

        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPerson(person);

        UserDTO dto = userConverter.convertToBasedDTO(user);

        Assert.assertNotNull(dto);
        Assert.assertEquals(1L, dto.getId().longValue());
        Assert.assertEquals("username", dto.getUsername());

        Assert.assertNotNull(dto.getPerson());
        Assert.assertEquals(2L, dto.getPerson().getId().longValue());
        Assert.assertEquals("Фамилия", dto.getPerson().getFirstName());
        Assert.assertEquals("Имя", dto.getPerson().getLastName());
        Assert.assertEquals("Отчество", dto.getPerson().getMiddleName());
    }

    @Test
    public void convertToEntityTest(){
        PersonDTO personDTO = PersonDTO.personBuilder()
                .id(2L)
                .build();

        personDTO.setFirstName("Фамилия");
        personDTO.setLastName("Имя");
        personDTO.setMiddleName("Отчество");

        UserDTO dto = UserDTO.userBuilder()
                .id(1L)
                .person(personDTO)
                .build();

        dto.setUsername("username");

        User user = userConverter.convertToEntity(dto);

        Assert.assertNotNull(user);
        Assert.assertEquals(1L, user.getId().longValue());
        Assert.assertEquals("username", user.getUsername());

        Assert.assertNotNull(dto.getPerson());
        Assert.assertEquals(2L, dto.getPerson().getId().longValue());
        Assert.assertEquals("Фамилия", dto.getPerson().getFirstName());
        Assert.assertEquals("Имя", dto.getPerson().getLastName());
        Assert.assertEquals("Отчество", dto.getPerson().getMiddleName());
    }

    @Test
    public void convertToDTOTest(){
        Person person = new Person();
        person.setId(2L);
        person.setFirstName("Фамилия");
        person.setLastName("Имя");
        person.setMiddleName("Отчество");

        LocalDateTime dateTime = LocalDateTime.now();

        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        user.setPasswordAttemptCount(2);
        user.setStatus(UserStatus.ACTIVE);
        user.setTimestampStatus(dateTime);
        user.setPerson(person);

        UserAdditionalDTO userAdditionalDTO = userConverter.convertToDTO(user);

        Assert.assertNotNull(userAdditionalDTO);
        Assert.assertEquals(1L, userAdditionalDTO.getId().longValue());
        Assert.assertEquals("username", userAdditionalDTO.getUsername());
        Assert.assertEquals("password", userAdditionalDTO.getPassword());
        Assert.assertEquals(2, userAdditionalDTO.getPasswordAttemptCount().intValue());
        Assert.assertEquals(ru.market.dto.user.UserStatus.ACTIVE, userAdditionalDTO.getStatus());
        Assert.assertEquals(dateTime, userAdditionalDTO.getTimestampStatus());

        Assert.assertNotNull(userAdditionalDTO.getPerson());
        Assert.assertEquals(2L, userAdditionalDTO.getPerson().getId().longValue());
        Assert.assertEquals("Фамилия", userAdditionalDTO.getPerson().getFirstName());
        Assert.assertEquals("Имя", userAdditionalDTO.getPerson().getLastName());
        Assert.assertEquals("Отчество", userAdditionalDTO.getPerson().getMiddleName());
    }

    @Test
    public void convertToUserAdditionalDTOTest(){
        RegistrationDTO registrationDTO = RegistrationDTO.builder()
                .username("username")
                .password("password")
                .build();

        UserAdditionalDTO userAdditionalDTO = userConverter.convertToUserAdditionalDTO(registrationDTO);

        Assert.assertNotNull(userAdditionalDTO);
        Assert.assertEquals("username", userAdditionalDTO.getUsername());
        Assert.assertEquals("password", userAdditionalDTO.getPassword());
    }

    @Test
    public void convertToPersonNoIdDTOTest(){
        RegistrationDTO registrationDTO = RegistrationDTO.builder()
                .firstName("Фамилия")
                .lastName("Имя")
                .middleName("Отчество")
                .build();

        PersonNoIdDTO personNoIdDTO = userConverter.convertToPersonNoIdDTO(registrationDTO);

        Assert.assertNotNull(personNoIdDTO);
        Assert.assertEquals("Фамилия", personNoIdDTO.getFirstName());
        Assert.assertEquals("Имя", personNoIdDTO.getLastName());
        Assert.assertEquals("Отчество", personNoIdDTO.getMiddleName());
    }
}