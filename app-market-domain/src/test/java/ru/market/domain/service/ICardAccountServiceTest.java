package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.dto.card.CardAccountDTO;
import ru.market.dto.card.CardAccountNoIdDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:data-test-config.properties")
public class ICardAccountServiceTest {
    @Autowired
    private ICardAccountService cardAccountService;

    @Test
    public void createTest(){
        CardAccountNoIdDTO noIdDTO = new CardAccountNoIdDTO();
        noIdDTO.setNumber("7777-8888-8898-1111");
        noIdDTO.setBalance("250");

        CardAccountDTO dto = cardAccountService.create(noIdDTO);

        Assert.assertNotNull(dto);
        Assert.assertNotNull(dto.getId());
    }
}
