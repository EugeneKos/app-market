package ru.market.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainServiceConfiguration;
import ru.market.domain.data.dto.UserDTO;
import ru.market.domain.data.dto.UserWithPasswordDTO;
import ru.market.domain.data.enums.UserRole;
import ru.market.domain.service.IUserService;
import ru.market.domain.utils.DTOUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainServiceConfiguration.class)
@TestPropertySource("classpath:data-config-main.properties")
public class DBCreation {
    @Autowired
    private IUserService userService;

    @Test
    public void initDB(){
        UserWithPasswordDTO dto = DTOUtils.createUserWithPasswordDTO(
                "Admin", "Admin", "Admin", "admin", "admin"
        );

        dto.setUserRole(UserRole.ROLE_ADMIN);
        UserDTO admin = userService.create(dto);
        Assert.assertNotNull(admin);
    }
}
