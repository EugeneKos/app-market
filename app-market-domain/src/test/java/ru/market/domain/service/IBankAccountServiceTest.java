package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.dto.bank.BankAccountDTO;
import ru.market.dto.bank.BankAccountNoIdDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:data-test-config.properties")
public class IBankAccountServiceTest {
    @Autowired
    private IBankAccountService bankAccountService;

    @Test
    public void createTest(){
        BankAccountNoIdDTO bankAccountNoIdDTO = new BankAccountNoIdDTO();
        bankAccountNoIdDTO.setBalance("30000");
        bankAccountNoIdDTO.setDescription("bank account test");

        BankAccountDTO bankAccountDTO = bankAccountService.create(bankAccountNoIdDTO);

        Assert.assertNotNull(bankAccountDTO);
        Assert.assertNotNull(bankAccountDTO.getId());
    }
}