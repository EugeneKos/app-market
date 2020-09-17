package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.data.session.api.SessionDataManager;
import ru.market.domain.service.ICostService;
import ru.market.dto.cost.CostDTO;

import java.util.Set;

@RestController
public class CostController {
    private ICostService costService;
    private SessionDataManager sessionDataManager;

    @Autowired
    public CostController(ICostService costService, SessionDataManager sessionDataManager) {
        this.costService = costService;
        this.sessionDataManager = sessionDataManager;
    }

    @RequestMapping(path = "/cost", method = RequestMethod.PUT, produces = "application/json")
    public CostDTO create(){
        return costService.create(sessionDataManager.getCurrentRequestBody());
    }

    @RequestMapping(path = "/cost", method = RequestMethod.POST, produces = "application/json")
    public CostDTO update(){
        return costService.update(sessionDataManager.getCurrentRequestBody());
    }

    @RequestMapping(path = "/cost/{costLimitId}", method = RequestMethod.GET, produces = "application/json")
    public Set<CostDTO> getAllByCostLimitId(@PathVariable(name = "costLimitId") Long costLimitId){
        return costService.getAllByCostLimitId(costLimitId);
    }

    @RequestMapping(path = "/cost/{costLimitId}/{dateStr}", method = RequestMethod.GET, produces = "application/json")
    public Set<CostDTO> getAllByCostLimitIdAndDate(@PathVariable(name = "costLimitId") Long costLimitId,
                                                   @PathVariable(name = "dateStr") String dateStr){

        return costService.getAllByCostLimitIdAndDate(costLimitId, dateStr);
    }

    @RequestMapping(path = "/cost/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable(name = "id") Long id){
        costService.deleteById(id);
    }
}
