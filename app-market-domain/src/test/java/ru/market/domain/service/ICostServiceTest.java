package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;
import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;
import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:database-test-config.properties")
public class ICostServiceTest {
    @Autowired
    private ICostService costService;

    @Autowired
    private IMoneyAccountService moneyAccountService;

    @Autowired
    private ICostLimitService costLimitService;

    @Test
    public void costServiceTest(){
        CostDTO costDTO = createTest();
        costDTO = updateTest(costDTO);
        getAllByCostLimitIdAndDateTest(costDTO.getCostLimitId());
        getAllIdByCostLimitIdsTest(Collections.singleton(costDTO.getId()));
        deleteByIdTest(costDTO);
    }

    private Long getMoneyAccountId(){
        MoneyAccountNoIdDTO dto = MoneyAccountNoIdDTO.builder()
                .balance("20000")
                .name("Test money account")
                .description("Test money account")
                .build();

        MoneyAccountDTO moneyAccountDTO = moneyAccountService.create(dto);

        return moneyAccountDTO.getId();
    }

    private Long getCostLimitId(){
        CostLimitNoIdDTO dto = CostLimitNoIdDTO.builder()
                .sum("10000")
                .description("Test cost limit")
                .build();

        CostLimitDTO costLimitDTO = costLimitService.create(dto);

        return costLimitDTO.getId();
    }

    private CostDTO createTest(){
        CostNoIdDTO dto = CostNoIdDTO.builder()
                .sum("200")
                .category("Тест категория")
                .description("Тест описание")
                .moneyAccountId(getMoneyAccountId())
                .costLimitId(getCostLimitId())
                .build();

        System.out.println("---------- create ----------");
        CostDTO costDTO = costService.create(dto);
        System.out.println("----------------------------");

        Assert.assertNotNull(costDTO);
        Assert.assertEquals("200", costDTO.getSum());
        Assert.assertEquals("Тест категория", costDTO.getCategory());
        Assert.assertEquals("Тест описание", costDTO.getDescription());

        return costDTO;
    }

    private CostDTO updateTest(CostDTO dto){
        dto.setSum("250");

        System.out.println("---------- update ----------");
        CostDTO updated = costService.update(dto);
        System.out.println("----------------------------");

        Assert.assertNotNull(updated);
        Assert.assertEquals("250", updated.getSum());

        return updated;
    }

    private void getAllByCostLimitIdAndDateTest(Long costLimitId){
        String nowStr = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDate.now());

        System.out.println("---------- getAllByCostLimitIdAndDate ----------");
        Set<CostDTO> costs = costService.getAllByCostLimitIdAndDate(costLimitId, nowStr);
        System.out.println("------------------------------------------------");

        Assert.assertNotNull(costs);
    }

    private void getAllIdByCostLimitIdsTest(Set<Long> costLimitIds){
        System.out.println("---------- getAllIdByCostLimitIds ----------");
        Set<Long> ids = costService.getAllIdByCostLimitIds(costLimitIds);
        System.out.println("--------------------------------------------");

        Assert.assertNotNull(ids);
    }

    private void deleteByIdTest(CostDTO dto){
        System.out.println("---------- deleteById ----------");
        costService.deleteById(dto.getId());
        System.out.println("--------------------------------");
    }
}