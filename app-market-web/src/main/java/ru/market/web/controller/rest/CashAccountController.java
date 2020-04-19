package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.domain.service.ICashAccountService;
import ru.market.dto.cash.CashAccountDTO;
import ru.market.dto.cash.CashAccountNoIdDTO;

import java.util.Set;

@RestController
public class CashAccountController {
    private ICashAccountService cashAccountService;

    @Autowired
    public CashAccountController(ICashAccountService cashAccountService) {
        this.cashAccountService = cashAccountService;
    }

    @RequestMapping(path = "/cash", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    public CashAccountDTO create(@RequestBody CashAccountNoIdDTO cashAccountNoIdDTO){
        return cashAccountService.create(cashAccountNoIdDTO);
    }

    @RequestMapping(path = "/cash/{id}", method = RequestMethod.GET, produces = "application/json")
    public CashAccountDTO getById(@PathVariable(name = "id") Long id){
        return cashAccountService.getById(id);
    }

    @RequestMapping(path = "/cash/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable(name = "id") Long id){
        cashAccountService.deleteById(id);
    }

    @RequestMapping(path = "/cash", method = RequestMethod.GET)
    public Set<CashAccountDTO> getAll(){
        return cashAccountService.getAll();
    }
}