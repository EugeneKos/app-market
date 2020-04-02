package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.domain.service.ICardAccountService;
import ru.market.dto.card.CardAccountDTO;
import ru.market.dto.card.CardAccountNoIdDTO;

import java.util.Set;

@RestController
public class CardAccountController {
    private ICardAccountService cardAccountService;

    @Autowired
    public CardAccountController(ICardAccountService cardAccountService) {
        this.cardAccountService = cardAccountService;
    }

    @RequestMapping(path = "/card", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    public CardAccountDTO create(@RequestBody CardAccountNoIdDTO cardAccountNoIdDTO){
        return cardAccountService.create(cardAccountNoIdDTO);
    }

    @RequestMapping(path = "/card/{id}", method = RequestMethod.GET, produces = "application/json")
    public CardAccountDTO getById(@PathVariable(name = "id") Long id){
        return cardAccountService.getById(id);
    }

    @RequestMapping(path = "/card/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable(name = "id") Long id){
        cardAccountService.deleteById(id);
    }

    @RequestMapping(path = "/card", method = RequestMethod.GET)
    public Set<CardAccountDTO> getAll(){
        return cardAccountService.getAll();
    }
}