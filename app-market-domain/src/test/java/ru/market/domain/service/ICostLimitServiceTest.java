package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:data-test-config.properties")
public class ICostLimitServiceTest {
    @Autowired
    private ICostLimitService costLimitService;

    @Test
    public void createTest(){
        CostLimitNoIdDTO costLimitNoIdDTO = new CostLimitNoIdDTO();
        costLimitNoIdDTO.setSum("25000");
        //costLimitNoIdDTO.setBeginDateStr("01-06-2020");
        //costLimitNoIdDTO.setEndDateStr("25-06-2020");
        costLimitNoIdDTO.setDescription("Тестовый лимит");

        CostLimitDTO costLimitDTO = costLimitService.create(costLimitNoIdDTO);

        Assert.assertNotNull(costLimitDTO);
        Assert.assertNotNull(costLimitDTO.getId());
    }
}