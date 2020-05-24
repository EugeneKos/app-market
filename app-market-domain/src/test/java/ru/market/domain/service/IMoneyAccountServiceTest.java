package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:data-test-config.properties")
public class IMoneyAccountServiceTest {
    @Autowired
    private IMoneyAccountService moneyAccountService;

    @Test
    public void createTest(){
        MoneyAccountNoIdDTO noIdDTO = new MoneyAccountNoIdDTO();
        noIdDTO.setBalance("150.20");
        noIdDTO.setName("Тестовый денежный счет");
        noIdDTO.setDescription("Тестовый денежный счет");

        MoneyAccountDTO dto = moneyAccountService.create(noIdDTO);

        Assert.assertNotNull(dto);
        Assert.assertNotNull(dto.getId());
        Assert.assertNotNull(dto.getDateCreatedStr());
        Assert.assertEquals("150.20", dto.getBalance());
        Assert.assertEquals("Тестовый денежный счет", dto.getName());
        Assert.assertEquals("Тестовый денежный счет", dto.getDescription());
    }
}