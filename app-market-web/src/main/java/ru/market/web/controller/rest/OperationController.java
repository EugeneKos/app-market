package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.data.session.api.RequestBodyManagement;
import ru.market.domain.service.IOperationService;

import ru.market.dto.operation.OperationDTO;
import ru.market.dto.result.ResultDTO;

import java.util.Set;

@RestController
@RequestMapping(path = "/operation")
public class OperationController {
    private IOperationService operationService;
    private RequestBodyManagement requestBodyManagement;

    @Autowired
    public OperationController(IOperationService operationService, RequestBodyManagement requestBodyManagement) {
        this.operationService = operationService;
        this.requestBodyManagement = requestBodyManagement;
    }

    @RequestMapping(path = "/enrollment", method = RequestMethod.PUT, produces = "application/json")
    public ResultDTO enrollment(){
        return operationService.enrollment(requestBodyManagement.getCurrentRequestBody());
    }

    @RequestMapping(path = "/debit", method = RequestMethod.PUT, produces = "application/json")
    public ResultDTO debit(){
        return operationService.debit(requestBodyManagement.getCurrentRequestBody());
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.PUT, produces = "application/json")
    public ResultDTO transfer(){
        return operationService.transfer(requestBodyManagement.getCurrentRequestBody());
    }

    @RequestMapping(path = "/account/{accountId}", method = RequestMethod.GET, produces = "application/json")
    public Set<OperationDTO> getAllByAccountId(@PathVariable(name = "accountId") Long accountId){
        return operationService.getAllByAccountId(accountId);
    }
}
