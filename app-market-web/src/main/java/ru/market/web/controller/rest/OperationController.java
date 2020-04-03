package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.domain.service.IOperationService;

import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationResultDTO;
import ru.market.dto.operation.OperationTransferDTO;

import java.util.Set;

@RestController
@RequestMapping(path = "/operation")
public class OperationController {
    private IOperationService operationService;

    @Autowired
    public OperationController(IOperationService operationService) {
        this.operationService = operationService;
    }

    @RequestMapping(path = "/enrollment", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    public OperationResultDTO enrollment(@RequestBody OperationEnrollDebitDTO enrollDebitDTO){
        return operationService.enrollment(enrollDebitDTO);
    }

    @RequestMapping(path = "/debit", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    public OperationResultDTO debit(@RequestBody OperationEnrollDebitDTO enrollDebitDTO){
        return operationService.debit(enrollDebitDTO);
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    public OperationResultDTO transfer(@RequestBody OperationTransferDTO transferDTO){
        return operationService.transfer(transferDTO);
    }

    @RequestMapping(path = "/account/{accountId}", method = RequestMethod.GET, produces = "application/json")
    public Set<OperationDTO> getAllByAccountId(@PathVariable(name = "accountId") Long accountId){
        return operationService.getAllByAccountId(accountId);
    }
}
