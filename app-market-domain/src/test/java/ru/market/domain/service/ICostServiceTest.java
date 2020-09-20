package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.domain.data.Cost;
import ru.market.domain.data.CostLimit;
import ru.market.domain.repository.CostLimitRepository;
import ru.market.domain.repository.CostRepository;
import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;
import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;
import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:app-market-test.properties")
public class ICostServiceTest {
    @Autowired
    private ICostService costService;

    @Autowired
    private IMoneyAccountService moneyAccountService;

    @Autowired
    private ICostLimitService costLimitService;

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private CostLimitRepository costLimitRepository;

    @Test
    public void costServiceTest(){
        CostDTO costDTO = createTest();
        costDTO = updateTest(costDTO);
        getAllByCostLimitIdTest(costDTO.getCostLimitId());
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

    private void getAllByCostLimitIdTest(Long costLimitId){
        CostLimit costLimit = costLimitRepository.findById(costLimitId)
                .orElseThrow(() -> new NullPointerException("Cost limit is null"));

        for (int i = 0; i < 9; i++) {
            Cost cost = new Cost();
            cost.setSum(new BigDecimal(45 * (i + 1)));
            cost.setCategory("Тест категория" + i);
            cost.setDescription("Тест описание" + i);
            cost.setDate(LocalDate.now().plusDays(i));
            cost.setTime(LocalTime.now());
            cost.setCostLimit(costLimit);

            costRepository.saveAndFlush(cost);
        }

        System.out.println("---------- getAllByCostLimitId ----------");
        Set<CostDTO> costs = costService.getAllByCostLimitId(costLimitId);
        System.out.println("-----------------------------------------");

        Assert.assertNotNull(costs);
        Assert.assertEquals(10, costs.size());
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