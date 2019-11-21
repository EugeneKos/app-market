package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainSpringConfiguration;
import ru.market.domain.data.dto.UserDTO;
import ru.market.domain.data.dto.UserWithPasswordDTO;
import ru.market.domain.data.enums.UserRole;
import ru.market.domain.utils.DTOUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainSpringConfiguration.class)
@TestPropertySource("classpath:data-config-test.properties")
public class UserServiceTest {
    @Autowired
    private IUserService userService;

    @Test
    public void createTest(){
        UserWithPasswordDTO dto = DTOUtils.createUserWithPasswordDTO(
                "Admin", "Admin", "Admin", "admin", "admin"
        );

        dto.setUserRole(UserRole.ROLE_ADMIN);
        UserDTO updated = userService.create(dto);
        Assert.assertNotNull(updated);
        Assert.assertNotNull(updated.getId());
        Assert.assertEquals("admin", updated.getLogin());
    }
}