package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.domain.service.ICostService;
import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;

import java.util.Set;

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

    @RequestMapping(path = "/cost/{costLimitId}/{dateStr}", method = RequestMethod.GET, produces = "application/json")
    public Set<CostDTO> getAllByCostLimitIdAndDate(@PathVariable(name = "costLimitId") Long costLimitId,
                                                   @PathVariable(name = "dateStr") String dateStr){

        return costService.getAllByCostLimitIdAndDate(costLimitId, dateStr);
    }
}
