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
import ru.market.domain.utils.DTOUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainSpringConfiguration.class)
@TestPropertySource("classpath:data-config-test.properties")
public class UserServiceTest {
    @Autowired
    private IUserService userService;

    @Test
    public void updateTest(){
        UserDTO dto = DTOUtils.createUserDTO("Eugene", "Connor", null, "MyLogin");
        UserDTO updated = userService.update(dto);
        Assert.assertNotNull(updated);
        Assert.assertNotNull(updated.getId());
        Assert.assertEquals("MyLogin", updated.getLogin());

        updated.setLogin("UpdatedLogin");
        updated = userService.update(updated);
        Assert.assertEquals("UpdatedLogin", updated.getLogin());
    }
}