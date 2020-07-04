package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.dto.result.ResultDTO;
import ru.market.dto.result.ResultStatus;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserAdditionalDTO;
import ru.market.dto.user.UserPasswordDTO;
import ru.market.dto.user.UserStatus;
import ru.market.dto.user.UserUsernameDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:database-test-config.properties")
public class IUserServiceTest {
    @Autowired
    private IUserService userService;

    @Test
    public void userServiceTest(){
        UserDTO userDTO = registrationTest();
        userDTO = changeUsernameTest(userDTO);
        changePasswordTest(userDTO);
        getByIdTest(userDTO);
        UserAdditionalDTO userAdditionalDTO = getByUsernameTest(userDTO);
        updateTest(userAdditionalDTO);
        deleteByIdTest(userAdditionalDTO.getId());
    }

    private UserDTO registrationTest(){
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setFirstName("Иван");
        registrationDTO.setLastName("Иванов");
        registrationDTO.setMiddleName("Иванович");
        registrationDTO.setUsername("ivanov");
        registrationDTO.setPassword("ivanovII2");

        System.out.println("---------- registration ----------");
        UserDTO userDTO = userService.registration(registrationDTO);
        System.out.println("----------------------------------");

        Assert.assertNotNull(userDTO);
        Assert.assertNotNull(userDTO.getId());

        return userDTO;
    }

    private UserDTO changeUsernameTest(UserDTO userDTO){
        System.out.println("---------- changeUsername ----------");
        UserDTO changedUser = userService.changeUsername(
                UserUsernameDTO.builder()
                        .username("ivanov1")
                        .build(),
                userDTO.getId()
        );
        System.out.println("------------------------------------");

        Assert.assertNotNull(changedUser);
        Assert.assertEquals("ivanov1", changedUser.getUsername());

        return changedUser;
    }

    private void changePasswordTest(UserDTO userDTO){
        System.out.println("---------- changePassword ----------");
        ResultDTO resultDTO = userService.changePassword(
                UserPasswordDTO.builder()
                        .oldPassword("ivanovII2")
                        .newPassword("ivanovII3")
                        .build(),
                userDTO.getId()
        );
        System.out.println("------------------------------------");

        Assert.assertEquals(ResultStatus.SUCCESS, resultDTO.getStatus());
    }

    private void getByIdTest(UserDTO userDTO){
        System.out.println("---------- getById ----------");
        UserDTO founded = userService.getById(userDTO.getId());
        System.out.println("-----------------------------");

        Assert.assertNotNull(founded);
    }

    private UserAdditionalDTO getByUsernameTest(UserDTO userDTO){
        System.out.println("---------- getByUsername ----------");
        UserAdditionalDTO userAdditionalDTO = userService.getByUsername(userDTO.getUsername());
        System.out.println("-----------------------------------");

        Assert.assertNotNull(userAdditionalDTO);
        Assert.assertNotNull(userAdditionalDTO.getPassword());
        Assert.assertEquals(UserStatus.ACTIVE, userAdditionalDTO.getStatus());
        Assert.assertNotNull(userAdditionalDTO.getTimestampStatus());
        Assert.assertEquals(Integer.valueOf(0), userAdditionalDTO.getPasswordAttemptCount());

        return userAdditionalDTO;
    }

    private void updateTest(UserAdditionalDTO userAdditionalDTO){
        System.out.println("---------- updateUserStatusByUsername ----------");
        userService.updateUserStatusByUsername(
                userAdditionalDTO.getUsername(), ru.market.domain.data.enumeration.UserStatus.TEMPORARY_LOCK
        );
        System.out.println("------------------------------------------------");

        System.out.println("---------- updatePasswordAttemptCountByUsername ----------");
        userService.updatePasswordAttemptCountByUsername(userAdditionalDTO.getUsername(), 2);
        System.out.println("----------------------------------------------------------");

        userAdditionalDTO = userService.getByUsername(userAdditionalDTO.getUsername());

        Assert.assertNotNull(userAdditionalDTO);
        Assert.assertEquals(UserStatus.TEMPORARY_LOCK, userAdditionalDTO.getStatus());
        Assert.assertEquals(Integer.valueOf(2), userAdditionalDTO.getPasswordAttemptCount());
    }

    private void deleteByIdTest(Long id){
        System.out.println("---------- deleteById ----------");
        userService.deleteById(id);
        System.out.println("--------------------------------");
    }
}