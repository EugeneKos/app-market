package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.domain.service.ICardService;
import ru.market.dto.card.CardDTO;
import ru.market.dto.card.CardNoIdDTO;

import java.util.Set;

@RestController
public class CardController {
    private ICardService cardService;

    @Autowired
    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(path = "/card", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    public CardDTO create(@RequestBody CardNoIdDTO cardDTO){
        return cardService.create(cardDTO);
    }

    @RequestMapping(path = "/card/{id}", method = RequestMethod.GET, produces = "application/json")
    public CardDTO getById(@PathVariable(name = "id") Long id){
        return cardService.getById(id);
    }

    @RequestMapping(path = "/card/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable(name = "id") Long id){
        cardService.deleteById(id);
    }

    @RequestMapping(path = "/card", method = RequestMethod.GET)
    public Set<CardDTO> getAll(){
        return cardService.getAll();
    }
}