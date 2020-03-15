package ru.market.domain.converter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.ConverterConfiguration;
import ru.market.domain.data.Cash;
import ru.market.dto.cash.CashDTO;
import ru.market.dto.cash.CashNoIdDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class CashConverterTest {
    @Autowired
    private CashConverter cashConverter;

    @Test
    public void convertToCashTest(){
        CashNoIdDTO cashNoIdDTO = new CashNoIdDTO();
        cashNoIdDTO.setName("Мой кошелёк");
        cashNoIdDTO.setBalance("5000");
        cashNoIdDTO.setDescription("Тест кошелёк");

        Cash cash = cashConverter.convertToCash(cashNoIdDTO);

        Assert.assertNotNull(cash);
        Assert.assertNull(cash.getId());
        Assert.assertEquals("Мой кошелёк", cash.getName());
        Assert.assertEquals("5000", cash.getBalance());
        Assert.assertEquals("Тест кошелёк", cash.getDescription());

        CashDTO cashDTO = new CashDTO();
        cashDTO.setId(1L);

        cash = cashConverter.convertToCash(cashDTO);

        Assert.assertNotNull(cash);
        Assert.assertEquals(1L, cash.getId().longValue());
    }

    @Test
    public void convertToCashDTOTest(){
        Cash cash = new Cash();
        cash.setId(25L);
        cash.setName("Мой кошелёк");
        cash.setBalance("5000");
        cash.setDescription("Тест кошелёк");

        CashDTO cashDTO = cashConverter.convertToCashDTO(cash);

        Assert.assertNotNull(cashDTO);
        Assert.assertEquals(25L, cashDTO.getId().longValue());
        Assert.assertEquals("Мой кошелёк", cashDTO.getName());
        Assert.assertEquals("5000", cashDTO.getBalance());
        Assert.assertEquals("Тест кошелёк", cashDTO.getDescription());
    }
}