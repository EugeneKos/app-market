package ru.market.domain.converter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.ConverterConfiguration;
import ru.market.domain.data.Cost;
import ru.market.domain.data.CostLimit;
import ru.market.domain.data.MoneyAccount;
import ru.market.domain.data.Operation;
import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class CostConverterTest {
    @Autowired
    private CostConverter costConverter;

    @Test
    public void convertToBasedDTOTest(){
        CostLimit limit = new CostLimit();
        limit.setId(2L);

        MoneyAccount moneyAccount = new MoneyAccount();
        moneyAccount.setId(2L);

        Operation operation = new Operation();
        operation.setMoneyAccount(moneyAccount);

        Cost cost = new Cost();
        cost.setSum(new BigDecimal(250));
        cost.setDescription("Cost description test");
        cost.setCategory("Cost category test");
        cost.setDate(LocalDate.of(2020, 6, 15));
        cost.setTime(LocalTime.of(14, 25));
        cost.setCostLimit(limit);
        cost.setOperation(operation);

        CostNoIdDTO dto = costConverter.convertToBasedDTO(cost);

        Assert.assertNotNull(dto);
        Assert.assertEquals("250", dto.getSum());
        Assert.assertEquals("Cost description test", dto.getDescription());
        Assert.assertEquals("Cost category test", dto.getCategory());
        Assert.assertEquals("15-06-2020", dto.getDateStr());
        Assert.assertEquals("14:25", dto.getTimeStr());
        Assert.assertEquals(2L, dto.getCostLimitId().longValue());
        Assert.assertEquals(2L, dto.getMoneyAccountId().longValue());
    }

    @Test
    public void convertToEntityTest(){
        CostNoIdDTO dto = CostNoIdDTO.builder()
                .sum("350")
                .description("Cost description test")
                .category("Cost category test")
                .dateStr("15-06-2020")
                .timeStr("14:25")
                .build();

        Cost cost = costConverter.convertToEntity(dto);

        Assert.assertNotNull(cost);
        Assert.assertEquals(new BigDecimal(350), cost.getSum());
        Assert.assertEquals("Cost description test", cost.getDescription());
        Assert.assertEquals("Cost category test", cost.getCategory());
        Assert.assertEquals(LocalDate.of(2020, 6, 15), cost.getDate());
        Assert.assertEquals(LocalTime.of(14, 25), cost.getTime());
    }

    @Test
    public void convertToDTOTest(){
        CostLimit limit = new CostLimit();
        limit.setId(1L);

        MoneyAccount moneyAccount = new MoneyAccount();
        moneyAccount.setId(1L);

        Operation operation = new Operation();
        operation.setMoneyAccount(moneyAccount);

        Cost cost = new Cost();
        cost.setId(1L);
        cost.setSum(new BigDecimal(250));
        cost.setDescription("Cost description test");
        cost.setCategory("Cost category test");
        cost.setDate(LocalDate.of(2020, 6, 15));
        cost.setTime(LocalTime.of(14, 25));
        cost.setCostLimit(limit);
        cost.setOperation(operation);

        CostDTO dto = costConverter.convertToDTO(cost);

        Assert.assertNotNull(dto);
        Assert.assertEquals(1L, dto.getId().longValue());
        Assert.assertEquals("250", dto.getSum());
        Assert.assertEquals("Cost description test", dto.getDescription());
        Assert.assertEquals("Cost category test", dto.getCategory());
        Assert.assertEquals("15-06-2020", dto.getDateStr());
        Assert.assertEquals("14:25", dto.getTimeStr());
        Assert.assertEquals(1L, dto.getCostLimitId().longValue());
        Assert.assertEquals(1L, dto.getMoneyAccountId().longValue());
    }

    @Test
    public void convertToOperationEnrollDebitDTOTest(){
        CostNoIdDTO dto = CostNoIdDTO.builder()
                .sum("350")
                .description("Cost description test")
                .category("Cost category test")
                .dateStr("15-06-2020")
                .timeStr("14:25")
                .moneyAccountId(1L)
                .build();

        OperationEnrollDebitDTO enrollDebitDTO = costConverter.convertToOperationEnrollDebitDTO(dto);

        Assert.assertNotNull(enrollDebitDTO);
        Assert.assertEquals("350", enrollDebitDTO.getAmount());
        Assert.assertEquals("Cost description test", enrollDebitDTO.getDescription());
        Assert.assertEquals("15-06-2020", enrollDebitDTO.getDateStr());
        Assert.assertEquals("14:25", enrollDebitDTO.getTimeStr());
        Assert.assertEquals(1L, enrollDebitDTO.getMoneyAccountId().longValue());
    }
}