package ru.market.domain.converter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.ConverterConfiguration;
import ru.market.domain.data.CostLimit;
import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitInfoDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class CostLimitConverterTest {
    @Autowired
    private CostLimitConverter costLimitConverter;

    @Test
    public void convertToBasedDTOTest(){
        CostLimit costLimit = new CostLimit();
        costLimit.setSum(new BigDecimal(7000));
        costLimit.setDescription("Cost limit description test");
        costLimit.setBeginDate(LocalDate.of(2020, 5, 25));
        costLimit.setEndDate(LocalDate.of(2020, 6, 25));

        CostLimitNoIdDTO dto = costLimitConverter.convertToBasedDTO(costLimit);

        Assert.assertNotNull(dto);
        Assert.assertEquals("7000", dto.getSum());
        Assert.assertEquals("Cost limit description test", dto.getDescription());
        Assert.assertEquals("25-05-2020", dto.getBeginDateStr());
        Assert.assertEquals("25-06-2020", dto.getEndDateStr());
    }

    @Test
    public void convertToEntityTest(){
        CostLimitNoIdDTO dto = CostLimitNoIdDTO.builder()
                .sum("7000")
                .description("Cost limit description test")
                .beginDateStr("25-05-2020")
                .endDateStr("25-06-2020")
                .build();

        CostLimit costLimit = costLimitConverter.convertToEntity(dto);

        Assert.assertNotNull(costLimit);
        Assert.assertEquals(new BigDecimal(7000), costLimit.getSum());
        Assert.assertEquals("Cost limit description test", costLimit.getDescription());
        Assert.assertEquals(LocalDate.of(2020, 5, 25), costLimit.getBeginDate());
        Assert.assertEquals(LocalDate.of(2020, 6, 25), costLimit.getEndDate());
    }

    @Test
    public void convertToDTOTest(){
        CostLimit costLimit = new CostLimit();
        costLimit.setId(1L);
        costLimit.setSum(new BigDecimal(7000));
        costLimit.setDescription("Cost limit description test");
        costLimit.setBeginDate(LocalDate.of(2020, 5, 25));
        costLimit.setEndDate(LocalDate.of(2020, 6, 25));

        CostLimitDTO dto = costLimitConverter.convertToDTO(costLimit);

        Assert.assertNotNull(dto);
        Assert.assertEquals(1L, dto.getId().longValue());
        Assert.assertEquals("7000", dto.getSum());
        Assert.assertEquals("Cost limit description test", dto.getDescription());
        Assert.assertEquals("25-05-2020", dto.getBeginDateStr());
        Assert.assertEquals("25-06-2020", dto.getEndDateStr());
    }

    @Test
    public void convertToCostLimitInfoDTOTest(){
        CostLimit costLimit = new CostLimit();
        costLimit.setId(1L);
        costLimit.setSum(new BigDecimal(7000));
        costLimit.setDescription("Cost limit description test");
        costLimit.setBeginDate(LocalDate.of(2020, 5, 25));
        costLimit.setEndDate(LocalDate.of(2020, 6, 25));

        CostLimitInfoDTO limitInfoDTO = costLimitConverter.convertToCostLimitInfoDTO(costLimit);

        Assert.assertNotNull(limitInfoDTO);
        Assert.assertEquals(1L, limitInfoDTO.getId().longValue());
        Assert.assertEquals("7000", limitInfoDTO.getSum());
        Assert.assertEquals("Cost limit description test", limitInfoDTO.getDescription());
        Assert.assertEquals("25-05-2020", limitInfoDTO.getBeginDateStr());
        Assert.assertEquals("25-06-2020", limitInfoDTO.getEndDateStr());
    }
}