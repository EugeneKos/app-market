package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.domain.service.ICostLimitService;
import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

@RestController
public class CostLimitController {
    private ICostLimitService costLimitService;

    @Autowired
    public CostLimitController(ICostLimitService costLimitService) {
        this.costLimitService = costLimitService;
    }

    @RequestMapping(path = "/cost-limit", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    public CostLimitDTO create(@RequestBody CostLimitNoIdDTO costLimitNoIdDTO){
        return costLimitService.create(costLimitNoIdDTO);
    }
}
