package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.domain.service.ICostService;
import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;

@RestController
public class CostController {
    private ICostService costService;

    @Autowired
    public CostController(ICostService costService) {
        this.costService = costService;
    }

    @RequestMapping(path = "/cost", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    public CostDTO create(@RequestBody CostNoIdDTO costNoIdDTO){
        return costService.create(costNoIdDTO);
    }
}
