package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.domain.data.MoneyAccount;
import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;

import java.math.BigDecimal;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:database-test-config.properties")
public class IMoneyAccountServiceTest {
    @Autowired
    private IMoneyAccountService moneyAccountService;

    @Autowired
    private IPersonProvider personProvider;

    @Test
    public void moneyAccountServiceTest(){
        MoneyAccountDTO moneyAccountDTO = createTest();
        getByIdTest(moneyAccountDTO);
        MoneyAccount moneyAccount = getMoneyAccountByIdTest(moneyAccountDTO);
        updateTest(moneyAccount);
        getAllTest();
        getAllIdByPersonIdTest();
        deleteByIdTest(moneyAccountDTO);
    }


    private MoneyAccountDTO createTest(){
        MoneyAccountNoIdDTO noIdDTO = new MoneyAccountNoIdDTO();
        noIdDTO.setBalance("150.20");
        noIdDTO.setName("Тестовый денежный счет");
        noIdDTO.setDescription("Тестовый денежный счет");

        System.out.println("---------- create ----------");
        MoneyAccountDTO dto = moneyAccountService.create(noIdDTO);
        System.out.println("----------------------------");

        Assert.assertNotNull(dto);
        Assert.assertNotNull(dto.getId());
        Assert.assertNotNull(dto.getDateCreatedStr());
        Assert.assertEquals("150.20", dto.getBalance());
        Assert.assertEquals("Тестовый денежный счет", dto.getName());
        Assert.assertEquals("Тестовый денежный счет", dto.getDescription());

        return dto;
    }

    private void getByIdTest(MoneyAccountDTO dto){
        System.out.println("---------- getById ----------");
        MoneyAccountDTO moneyAccountDTO = moneyAccountService.getById(dto.getId());
        System.out.println("-----------------------------");

        Assert.assertNotNull(moneyAccountDTO);
    }

    private MoneyAccount getMoneyAccountByIdTest(MoneyAccountDTO dto){
        System.out.println("---------- getMoneyAccountById ----------");
        MoneyAccount moneyAccount = moneyAccountService.getMoneyAccountById(dto.getId());
        System.out.println("-----------------------------------------");

        Assert.assertNotNull(moneyAccount);

        return moneyAccount;
    }

    private void updateTest(MoneyAccount moneyAccount){
        moneyAccount.setBalance(new BigDecimal(300));

        System.out.println("---------- update ----------");
        moneyAccountService.update(moneyAccount);
        System.out.println("----------------------------");
    }

    private void getAllTest(){
        System.out.println("---------- getAll ----------");
        Set<MoneyAccountDTO> moneyAccounts = moneyAccountService.getAll();
        System.out.println("----------------------------");

        Assert.assertNotNull(moneyAccounts);
    }

    private void getAllIdByPersonIdTest(){
        System.out.println("---------- getAllIdByPersonId ----------");
        Set<Long> ids = moneyAccountService.getAllIdByPersonId(personProvider.getCurrentPersonId());
        System.out.println("----------------------------------------");

        Assert.assertNotNull(ids);
    }

    private void deleteByIdTest(MoneyAccountDTO dto){
        System.out.println("---------- deleteById ----------");
        moneyAccountService.deleteById(dto.getId());
        System.out.println("--------------------------------");
    }
}