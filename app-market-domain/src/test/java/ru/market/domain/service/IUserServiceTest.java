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
import ru.market.dto.user.UserAdditionalDTO;
import ru.market.dto.user.UserStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:data-test-config.properties")
public class IUserServiceTest {
    @Autowired
    private IUserService userService;

    @Test
    public void userServiceTest(){
        UserDTO userDTO = registrationTest();
        UserAdditionalDTO userAdditionalDTO = getByUsernameTest(userDTO);
        updateUserStatusByIdTest(userAdditionalDTO);
    }

    private UserDTO registrationTest(){
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setFirstName("Иван");
        registrationDTO.setLastName("Иванов");
        registrationDTO.setMiddleName("Иванович");
        registrationDTO.setUsername("ivanov");
        registrationDTO.setPassword("ivanovII2");

        UserDTO userDTO = userService.registration(registrationDTO);

        Assert.assertNotNull(userDTO);
        Assert.assertNotNull(userDTO.getId());

        return userDTO;
    }

    private UserAdditionalDTO getByUsernameTest(UserDTO userDTO){
        UserAdditionalDTO userAdditionalDTO = userService.getByUsername(userDTO.getUsername());

        Assert.assertNotNull(userAdditionalDTO);
        Assert.assertNotNull(userAdditionalDTO.getPassword());
        Assert.assertEquals(UserStatus.ACTIVE, userAdditionalDTO.getStatus());
        Assert.assertNotNull(userAdditionalDTO.getTimestampStatus());
        Assert.assertEquals(Integer.valueOf(0), userAdditionalDTO.getPasswordAttemptCount());

        return userAdditionalDTO;
    }

    private void updateUserStatusByIdTest(UserAdditionalDTO userAdditionalDTO){
        userService.updateUserStatusByUsername(
                userAdditionalDTO.getUsername(), ru.market.domain.data.enumeration.UserStatus.TEMPORARY_LOCK
        );
        userService.updatePasswordAttemptCountByUsername(userAdditionalDTO.getUsername(), 2);

        userAdditionalDTO = userService.getByUsername(userAdditionalDTO.getUsername());

        Assert.assertNotNull(userAdditionalDTO);
        Assert.assertEquals(UserStatus.TEMPORARY_LOCK, userAdditionalDTO.getStatus());
        Assert.assertEquals(Integer.valueOf(2), userAdditionalDTO.getPasswordAttemptCount());
    }
}