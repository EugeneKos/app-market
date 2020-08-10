package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.domain.data.CostLimit;
import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitInfoDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:app-market-test.properties")
public class ICostLimitServiceTest {
    @Autowired
    private ICostLimitService costLimitService;

    @Autowired
    private IPersonProvider personProvider;

    @Test
    public void costLimitServiceTest(){
        CostLimitDTO costLimitDTO = createTest();
        getCostLimitByIdTest(costLimitDTO);
        getCostLimitInfoByIdTest(costLimitDTO);
        getAllTest();
        getAllIdByPersonIdTest();
        deleteByIdTest(costLimitDTO);
    }

    private CostLimitDTO createTest(){
        CostLimitNoIdDTO costLimitNoIdDTO = new CostLimitNoIdDTO();
        costLimitNoIdDTO.setSum("25000");
        costLimitNoIdDTO.setDescription("Тестовый лимит");

        System.out.println("---------- create ----------");
        CostLimitDTO costLimitDTO = costLimitService.create(costLimitNoIdDTO);
        System.out.println("----------------------------");

        Assert.assertNotNull(costLimitDTO);
        Assert.assertNotNull(costLimitDTO.getId());

        return costLimitDTO;
    }

    private void getCostLimitByIdTest(CostLimitDTO dto){
        System.out.println("---------- getCostLimitById ----------");
        CostLimit costLimit = costLimitService.getCostLimitById(dto.getId());
        System.out.println("--------------------------------------");

        Assert.assertNotNull(costLimit);
    }

    private void getCostLimitInfoByIdTest(CostLimitDTO dto){
        String nowStr = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDate.now());

        System.out.println("---------- getCostLimitInfoById ----------");
        CostLimitInfoDTO costLimitInfo = costLimitService.getCostLimitInfoById(dto.getId(), nowStr);
        System.out.println("------------------------------------------");

        Assert.assertNotNull(costLimitInfo);
    }

    private void getAllTest(){
        System.out.println("---------- getAll ----------");
        Set<CostLimitDTO> costLimits = costLimitService.getAll();
        System.out.println("----------------------------");

        Assert.assertNotNull(costLimits);
    }

    private void getAllIdByPersonIdTest(){
        System.out.println("---------- getAllIdByPersonId ----------");
        Set<Long> ids = costLimitService.getAllIdByPersonId(personProvider.getCurrentPersonId());
        System.out.println("----------------------------------------");

        Assert.assertNotNull(ids);
    }

    private void deleteByIdTest(CostLimitDTO dto){
        System.out.println("---------- deleteById ----------");
        costLimitService.deleteById(dto.getId(), false);
        System.out.println("--------------------------------");
    }
}