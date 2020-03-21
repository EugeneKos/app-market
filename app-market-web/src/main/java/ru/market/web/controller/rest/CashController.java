package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.domain.service.ICashService;
import ru.market.dto.cash.CashDTO;
import ru.market.dto.cash.CashNoIdDTO;

import java.util.Set;

@RestController
public class CashController {
    private ICashService cashService;

    @Autowired
    public CashController(ICashService cashService) {
        this.cashService = cashService;
    }

    @RequestMapping(path = "/cash", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    public CashDTO create(@RequestBody CashNoIdDTO cashDTO){
        return cashService.create(cashDTO);
    }

    @RequestMapping(path = "/cash/{id}", method = RequestMethod.GET, produces = "application/json")
    public CashDTO getById(@PathVariable(name = "id") Long id){
        return cashService.getById(id);
    }

    @RequestMapping(path = "/cash/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable(name = "id") Long id){
        cashService.deleteById(id);
    }

    @RequestMapping(path = "/cash", method = RequestMethod.GET)
    public Set<CashDTO> getAll(){
        return cashService.getAll();
    }
}