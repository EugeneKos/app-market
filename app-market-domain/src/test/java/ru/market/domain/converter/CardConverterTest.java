package ru.market.domain.converter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.ConverterConfiguration;
import ru.market.domain.data.Card;
import ru.market.dto.card.CardDTO;
import ru.market.dto.card.CardNoIdDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class CardConverterTest {
    @Autowired
    private CardConverter cardConverter;

    @Test
    public void convertToCardTest(){
        CardNoIdDTO cardNoIdDTO = new CardNoIdDTO();
        cardNoIdDTO.setNumber("7777 7777 7777 7777");
        cardNoIdDTO.setBalance("25000");
        cardNoIdDTO.setDescription("Дебетовая тестовая карта");

        Card card = cardConverter.convertToCard(cardNoIdDTO);

        Assert.assertNotNull(card);
        Assert.assertNull(card.getId());
        Assert.assertEquals("7777 7777 7777 7777", card.getNumber());
        Assert.assertEquals("25000", card.getBalance());
        Assert.assertEquals("Дебетовая тестовая карта", card.getDescription());

        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(1L);

        card = cardConverter.convertToCard(cardDTO);

        Assert.assertNotNull(card);
        Assert.assertEquals(1L, card.getId().longValue());
    }

    @Test
    public void convertToCardDTOTest(){
        Card card = new Card();
        card.setId(25L);
        card.setNumber("7777 7777 7777 7777");
        card.setBalance("25000");
        card.setDescription("Дебетовая тестовая карта");

        CardDTO cardDTO = cardConverter.convertToCardDTO(card);

        Assert.assertNotNull(cardDTO);
        Assert.assertEquals(25L, cardDTO.getId().longValue());
        Assert.assertEquals("7777 7777 7777 7777", cardDTO.getNumber());
        Assert.assertEquals("25000", cardDTO.getBalance());
        Assert.assertEquals("Дебетовая тестовая карта", cardDTO.getDescription());
    }
}