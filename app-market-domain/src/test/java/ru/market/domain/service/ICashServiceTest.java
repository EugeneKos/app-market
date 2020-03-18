package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.dto.cash.CashDTO;
import ru.market.dto.cash.CashNoIdDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:data-test-config.properties")
public class ICashServiceTest {
    @Autowired
    private ICashService cashService;

    @Test
    public void createTest(){
        CashNoIdDTO cashNoIdDTO = createCashDTO("Мой кошелёк", "5000", "Тест кошелёк");

        CashDTO cashDTO = cashService.create(cashNoIdDTO);

        Assert.assertNotNull(cashDTO);
        Assert.assertNotNull(cashDTO.getId());
    }

    @Test
    public void updateTest(){
        CashNoIdDTO cashNoIdDTO = createCashDTO("Мой кошелёк 2", "6000", "Тест кошелёк 2");

        CashDTO cashDTO = cashService.create(cashNoIdDTO);

        Assert.assertNotNull(cashDTO);
        Assert.assertNotNull(cashDTO.getId());
        Assert.assertEquals("Мой кошелёк 2", cashDTO.getName());
        Assert.assertEquals("6000", cashDTO.getBalance());
        Assert.assertEquals("Тест кошелёк 2", cashDTO.getDescription());

        cashDTO.setBalance("6500");
        cashDTO = cashService.update(cashDTO);

        Assert.assertNotNull(cashDTO);
        Assert.assertEquals("6500", cashDTO.getBalance());
    }

    @Test
    public void getByIdTest(){
        CashNoIdDTO cashNoIdDTO = createCashDTO("Мой кошелёк 3", "5900", "Тест кошелёк 3");

        CashDTO cashDTO = cashService.create(cashNoIdDTO);
        Assert.assertNotNull(cashDTO);

        CashDTO founded = cashService.getById(cashDTO.getId());
        Assert.assertNotNull(founded);
    }

    @Test
    public void deleteByIdTest(){
        CashNoIdDTO cashNoIdDTO = createCashDTO("Мой кошелёк 4", "9500", "Тест кошелёк 4");

        CashDTO cashDTO = cashService.create(cashNoIdDTO);
        Assert.assertNotNull(cashDTO);

        cashService.deleteById(cashDTO.getId());

        cashDTO = cashService.getById(cashDTO.getId());
        Assert.assertNull(cashDTO);
    }

    private CashNoIdDTO createCashDTO(String name, String balance, String description){
        CashNoIdDTO cashDTO = new CashNoIdDTO();
        cashDTO.setName(name);
        cashDTO.setBalance(balance);
        cashDTO.setDescription(description);
        return cashDTO;
    }
}