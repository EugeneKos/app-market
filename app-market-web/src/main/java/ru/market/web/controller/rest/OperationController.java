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
import ru.market.dto.operation.OperationResultDTO;

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
    public OperationResultDTO enrollment(){
        return operationService.enrollment(sessionDataManager.getCurrentRequestBody());
    }

    @RequestMapping(path = "/debit", method = RequestMethod.PUT, produces = "application/json")
    public OperationResultDTO debit(){
        return operationService.debit(sessionDataManager.getCurrentRequestBody());
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.PUT, produces = "application/json")
    public OperationResultDTO transfer(){
        return operationService.transfer(sessionDataManager.getCurrentRequestBody());
    }

    @RequestMapping(path = "/money-account/{moneyAccountId}", method = RequestMethod.GET, produces = "application/json")
    public Set<OperationDTO> getAllByMoneyAccountId(@PathVariable(name = "moneyAccountId") Long moneyAccountId){
        return operationService.getAllByMoneyAccountId(moneyAccountId);
    }

    @RequestMapping(path = "/money-account/{moneyAccountId}", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public Set<OperationDTO> getAllByMoneyAccountIdAndFilter(@PathVariable(name = "moneyAccountId") Long moneyAccountId,
                                                             @RequestBody OperationFilterDTO filterDTO){

        return operationService.getAllByMoneyAccountIdAndFilter(moneyAccountId, filterDTO);
    }
}
