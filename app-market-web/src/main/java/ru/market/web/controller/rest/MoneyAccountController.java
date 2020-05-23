package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.domain.service.IMoneyAccountService;
import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;

import java.util.Set;

@RestController
public class MoneyAccountController {
    private IMoneyAccountService moneyAccountService;

    @Autowired
    public MoneyAccountController(IMoneyAccountService moneyAccountService) {
        this.moneyAccountService = moneyAccountService;
    }

    @RequestMapping(path = "/money-account", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    public MoneyAccountDTO create(@RequestBody MoneyAccountNoIdDTO moneyAccountNoIdDTO){
        return moneyAccountService.create(moneyAccountNoIdDTO);
    }

    @RequestMapping(path = "/money-account/{id}", method = RequestMethod.GET, produces = "application/json")
    public MoneyAccountDTO getById(@PathVariable(name = "id") Long id){
        return moneyAccountService.getById(id);
    }

    @RequestMapping(path = "/money-account/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable(name = "id") Long id){
        moneyAccountService.deleteById(id);
    }

    @RequestMapping(path = "/money-account", method = RequestMethod.GET)
    public Set<MoneyAccountDTO> getAll(){
        return moneyAccountService.getAll();
    }
}
