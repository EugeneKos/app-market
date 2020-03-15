package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainConfiguration;
import ru.market.dto.card.CardDTO;
import ru.market.dto.card.CardNoIdDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainConfiguration.class)
@TestPropertySource(locations = "classpath:data-test-config.properties")
public class ICardServiceTest {
    @Autowired
    private ICardService cardService;

    @Test
    public void createTest(){
        CardNoIdDTO cardNoIdDTO = createCardDTO("5555 5555 5555 5555", "12000", "Тест карта");

        CardDTO cardDTO = cardService.create(cardNoIdDTO);

        Assert.assertNotNull(cardDTO);
        Assert.assertNotNull(cardDTO.getId());
    }

    @Test
    public void updateTest(){
        CardNoIdDTO cardNoIdDTO = createCardDTO("5555 5555 5555 5556", "15000", "Тест карта 2");

        CardDTO cardDTO = cardService.create(cardNoIdDTO);

        Assert.assertNotNull(cardDTO);
        Assert.assertNotNull(cardDTO.getId());
        Assert.assertEquals("5555 5555 5555 5556", cardDTO.getNumber());
        Assert.assertEquals("15000", cardDTO.getBalance());
        Assert.assertEquals("Тест карта 2", cardDTO.getDescription());

        cardDTO.setBalance("15500");
        cardDTO = cardService.update(cardDTO);

        Assert.assertNotNull(cardDTO);
        Assert.assertEquals("15500", cardDTO.getBalance());
    }

    @Test
    public void getByIdTest(){
        CardNoIdDTO cardNoIdDTO = createCardDTO("5555 5555 5555 5557", "5900", "Тест карта 3");

        CardDTO cardDTO = cardService.create(cardNoIdDTO);
        Assert.assertNotNull(cardDTO);

        CardDTO founded = cardService.getById(cardDTO.getId());
        Assert.assertNotNull(founded);
    }

    @Test
    public void deleteByIdTest(){
        CardNoIdDTO cardNoIdDTO = createCardDTO("5555 5555 5555 5558", "9500", "Тест карта 4");

        CardDTO cardDTO = cardService.create(cardNoIdDTO);
        Assert.assertNotNull(cardDTO);

        cardService.deleteById(cardDTO.getId());

        cardDTO = cardService.getById(cardDTO.getId());
        Assert.assertNull(cardDTO);
    }

    private CardNoIdDTO createCardDTO(String number, String balance, String description){
        CardNoIdDTO cardDTO = new CardNoIdDTO();
        cardDTO.setNumber(number);
        cardDTO.setBalance(balance);
        cardDTO.setDescription(description);
        return cardDTO;
    }
}