package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.dto.cash.CashAccountDTO;
import ru.market.dto.cash.CashAccountNoIdDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:data-test-config.properties")
public class ICashAccountServiceTest {
    @Autowired
    private ICashAccountService cashAccountService;

    @Test
    public void createTest(){
        CashAccountNoIdDTO noIdDTO = new CashAccountNoIdDTO();
        noIdDTO.setName("Кошелёк");
        noIdDTO.setBalance("700");

        CashAccountDTO dto = cashAccountService.create(noIdDTO);

        Assert.assertNotNull(dto);
        Assert.assertNotNull(dto.getId());
    }
}
