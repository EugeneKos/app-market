package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.OperationConverter;
import ru.market.domain.data.MoneyAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.Person;
import ru.market.domain.data.enumeration.OperationType;
import ru.market.domain.repository.OperationRepository;
import ru.market.domain.service.IMoneyAccountService;
import ru.market.domain.service.OperationExecutor;
import ru.market.domain.service.utils.OperationHelper;
import ru.market.domain.validator.CommonValidator;
import ru.market.domain.validator.operation.OperationValidator;

import ru.market.dto.operation.OperationBasedDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationResultDTO;
import ru.market.dto.operation.OperationTransferDTO;
import ru.market.dto.result.ResultStatus;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class OperationExecutorImpl implements OperationExecutor {
    private OperationRepository operationRepository;
    private OperationConverter operationConverter;

    private IMoneyAccountService moneyAccountService;

    public OperationExecutorImpl(OperationRepository operationRepository,
                                 OperationConverter operationConverter,
                                 IMoneyAccountService moneyAccountService) {

        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
        this.moneyAccountService = moneyAccountService;
    }

    @Transactional
    @Override
    public OperationResultDTO execute(OperationEnrollDebitDTO enrollDebitDTO, OperationType operationType,
                                      BiFunction<MoneyAccount, Operation, OperationResultDTO> function) {

        MoneyAccount moneyAccount = moneyAccountService.getMoneyAccountById(enrollDebitDTO.getMoneyAccountId());

        Operation operation = convertAndValidate(enrollDebitDTO, moneyAccount);
        operation.setOperationType(operationType);

        OperationResultDTO result = function.apply(moneyAccount, operation);

        if(result.getStatus() != ResultStatus.SUCCESS){
            return result;
        }

        saveAndUpdate(moneyAccount, operation);

        result.setOperationId(operation.getId());
        return result;
    }

    @Transactional
    @Override
    public OperationResultDTO execute(OperationTransferDTO transferDTO) {
        MoneyAccount fromMoneyAccount = moneyAccountService.getMoneyAccountById(transferDTO.getFromMoneyAccountId());
        MoneyAccount toMoneyAccount = moneyAccountService.getMoneyAccountById(transferDTO.getToMoneyAccountId());

        Operation operation = convertAndValidate(transferDTO, fromMoneyAccount);
        Operation debitOperation = operation.customClone();
        Operation enrollOperation = operation.customClone();

        OperationResultDTO result = OperationHelper.debit(fromMoneyAccount, debitOperation);

        if(result.getStatus() != ResultStatus.SUCCESS){
            return result;
        }

        OperationHelper.enrollment(toMoneyAccount, enrollOperation);

        prepareToSave(debitOperation, enrollOperation, fromMoneyAccount, toMoneyAccount);

        OperationResultDTO operationResultDTO = new OperationResultDTO(ResultStatus.SUCCESS, "Перевод выполнен");
        operationResultDTO.setOperationId(debitOperation.getId());
        return operationResultDTO;
    }

    private Operation convertAndValidate(OperationBasedDTO basedDTO, MoneyAccount moneyAccount){
        Operation operation = operationConverter.convertToEntity(basedDTO);
        CommonValidator<Operation> validator = new OperationValidator(moneyAccount);
        validator.validate(operation);
        return operation;
    }

    private void prepareToSave(Operation debitOperation, Operation enrollOperation,
                               MoneyAccount fromMoneyAccount, MoneyAccount toMoneyAccount) {

        Person fromAccountPerson = fromMoneyAccount.getPerson();
        Person toAccountPerson = toMoneyAccount.getPerson();

        debitOperation.setOperationType(OperationType.DEBIT);
        debitOperation.setDescription(transferDescription(fromAccountPerson, toAccountPerson,
                () -> "Перевод средств на счет ID: " + toMoneyAccount.getId(),
                () -> String.format("Перевод средств (%s %s %s)",
                        toAccountPerson.getLastName(),
                        toAccountPerson.getFirstName(),
                        toAccountPerson.getMiddleName())
        ));

        saveAndUpdate(fromMoneyAccount, debitOperation);

        enrollOperation.setOperationType(OperationType.ENROLLMENT);
        enrollOperation.setDescription(transferDescription(fromAccountPerson, toAccountPerson,
                () -> "Зачисление средств со счета ID: " + fromMoneyAccount.getId(),
                () -> String.format("Зачисление средств от (%s %s %s)",
                        fromAccountPerson.getLastName(),
                        fromAccountPerson.getFirstName(),
                        fromAccountPerson.getMiddleName())
        ));

        saveAndUpdate(toMoneyAccount, enrollOperation);
    }

    private String transferDescription(Person fromAccountPerson, Person toAccountPerson,
                                       Supplier<String> supplierWithEqualPerson,
                                       Supplier<String> supplierWithNotEqualPerson){

        if(fromAccountPerson.equals(toAccountPerson)){
            return supplierWithEqualPerson.get();
        }

        return supplierWithNotEqualPerson.get();
    }

    private void saveAndUpdate(MoneyAccount moneyAccount, Operation operation){
        moneyAccountService.update(moneyAccount);
        operation.setMoneyAccount(moneyAccount);
        operationRepository.saveAndFlush(operation);
    }
}
