package ru.market.domain.converter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.ConverterConfiguration;
import ru.market.domain.data.MoneyAccount;
import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class MoneyAccountConverterTest {
    @Autowired
    private MoneyAccountConverter moneyAccountConverter;

    @Test
    public void convertToBasedDTOTest(){
        MoneyAccount moneyAccount = new MoneyAccount();
        moneyAccount.setBalance(new BigDecimal(10000));
        moneyAccount.setName("Test money account name");
        moneyAccount.setDescription("Test money account description");

        MoneyAccountNoIdDTO dto = moneyAccountConverter.convertToBasedDTO(moneyAccount);

        Assert.assertNotNull(dto);
        Assert.assertEquals("10000", dto.getBalance());
        Assert.assertEquals("Test money account name", dto.getName());
        Assert.assertEquals("Test money account description", dto.getDescription());
    }

    @Test
    public void convertToEntityTest(){
        MoneyAccountNoIdDTO dto = MoneyAccountNoIdDTO.builder()
                .balance("10000")
                .name("Test money account name")
                .description("Test money account description")
                .build();

        MoneyAccount moneyAccount = moneyAccountConverter.convertToEntity(dto);

        Assert.assertNotNull(moneyAccount);
        Assert.assertEquals(new BigDecimal(10000), moneyAccount.getBalance());
        Assert.assertEquals("Test money account name", moneyAccount.getName());
        Assert.assertEquals("Test money account description", moneyAccount.getDescription());
    }

    @Test
    public void convertToDTOTest(){
        MoneyAccount moneyAccount = new MoneyAccount();
        moneyAccount.setId(1L);
        moneyAccount.setBalance(new BigDecimal(10000));
        moneyAccount.setName("Test money account name");
        moneyAccount.setDescription("Test money account description");
        moneyAccount.setDateCreated(LocalDate.of(2020, 5, 25));

        MoneyAccountDTO moneyAccountDTO = moneyAccountConverter.convertToDTO(moneyAccount);

        Assert.assertNotNull(moneyAccountDTO);
        Assert.assertEquals(1L, moneyAccountDTO.getId().longValue());
        Assert.assertEquals("10000", moneyAccountDTO.getBalance());
        Assert.assertEquals("Test money account name", moneyAccountDTO.getName());
        Assert.assertEquals("Test money account description", moneyAccountDTO.getDescription());
        Assert.assertEquals("25-05-2020", moneyAccountDTO.getDateCreatedStr());
    }
}