package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.market.domain.service.ICostLimitService;
import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitInfoDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

import java.util.Set;

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

    @RequestMapping(path = "/cost-limit", method = RequestMethod.GET, produces = "application/json")
    public Set<CostLimitDTO> getAll(){
        return costLimitService.getAll();
    }

    @RequestMapping(path = "/cost-limit/info/{id}/{dateStr}", method = RequestMethod.GET, produces = "application/json")
    public CostLimitInfoDTO getCostLimitInfoById(@PathVariable(name = "id") Long id,
                                                 @PathVariable(name = "dateStr") String dateStr){

        return costLimitService.getCostLimitInfoById(id, dateStr);
    }

    @RequestMapping(path = "/cost-limit/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable(name = "id") Long id,
                           @RequestParam("rollbackOperations") Boolean rollbackOperations){

        costLimitService.deleteById(id, rollbackOperations);
    }
}
