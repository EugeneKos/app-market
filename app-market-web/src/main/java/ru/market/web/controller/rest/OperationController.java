package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.data.session.api.SessionDataManager;
import ru.market.domain.service.IOperationService;

import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationFilterDTO;
import ru.market.dto.result.ResultDTO;

import java.util.Set;

@RestController
@RequestMapping(path = "/operation")
public class OperationController {
    private IOperationService operationService;
    private SessionDataManager sessionDataManager;

    @Autowired
    public OperationController(IOperationService operationService, SessionDataManager sessionDataManager) {
        this.operationService = operationService;
        this.sessionDataManager = sessionDataManager;
    }

    @RequestMapping(path = "/enrollment", method = RequestMethod.PUT, produces = "application/json")
    public ResultDTO enrollment(){
        return operationService.enrollment(sessionDataManager.getCurrentRequestBody());
    }

    @RequestMapping(path = "/debit", method = RequestMethod.PUT, produces = "application/json")
    public ResultDTO debit(){
        return operationService.debit(sessionDataManager.getCurrentRequestBody());
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.PUT, produces = "application/json")
    public ResultDTO transfer(){
        return operationService.transfer(sessionDataManager.getCurrentRequestBody());
    }

    @RequestMapping(path = "/account/{accountId}", method = RequestMethod.GET, produces = "application/json")
    public Set<OperationDTO> getAllByAccountId(@PathVariable(name = "accountId") Long accountId){
        return operationService.getAllByAccountId(accountId);
    }

    @RequestMapping(path = "/account/{accountId}", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public Set<OperationDTO> getAllByAccountIdAndFilter(@PathVariable(name = "accountId") Long accountId,
                                                        @RequestBody OperationFilterDTO filterDTO){

        return operationService.getAllByAccountIdAndFilter(accountId, filterDTO);
    }
}
