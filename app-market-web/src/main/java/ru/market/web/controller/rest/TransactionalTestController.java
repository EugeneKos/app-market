package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.market.domain.service.ITransactionalTestService;

@RestController
public class TransactionalTestController {
    private ITransactionalTestService transactionalTestService;

    @Autowired
    public TransactionalTestController(ITransactionalTestService transactionalTestService) {
        this.transactionalTestService = transactionalTestService;
    }

    @RequestMapping(path = "/transactional/{id}")
    public void changeBalance(@PathVariable(name = "id") Long id){
        transactionalTestService.changeBalance(id);
    }

    @RequestMapping(path = "/transactional2/{id}")
    public void transactionalChangeBalance(@PathVariable(name = "id") Long id){
        transactionalTestService.transactionalChangeBalance(id);
    }
}
