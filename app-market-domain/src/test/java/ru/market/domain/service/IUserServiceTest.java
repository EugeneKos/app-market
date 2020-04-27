package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserSecretDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:data-test-config.properties")
public class IUserServiceTest {
    @Autowired
    private IUserService userService;

    @Test
    public void registrationTest(){
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setFirstName("Иван");
        registrationDTO.setLastName("Иванов");
        registrationDTO.setMiddleName("Иванович");
        registrationDTO.setUsername("ivanov");
        registrationDTO.setPassword("ivanovII2");

        UserDTO userDTO = userService.registration(registrationDTO);

        Assert.assertNotNull(userDTO);
        Assert.assertNotNull(userDTO.getId());

        UserSecretDTO userSecretDTO = userService.getByUsername("ivanov");

        Assert.assertNotNull(userSecretDTO);
        Assert.assertNotNull(userSecretDTO.getPassword());
        Assert.assertNotNull(userSecretDTO.getSecretKey());
    }

}