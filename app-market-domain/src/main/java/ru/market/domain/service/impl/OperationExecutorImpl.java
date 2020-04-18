package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.OperationConverter;
import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.Person;
import ru.market.domain.data.enumeration.OperationType;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.repository.common.OperationRepository;
import ru.market.domain.service.OperationExecutor;
import ru.market.domain.service.utils.OperationHelper;
import ru.market.domain.validator.CommonValidator;
import ru.market.domain.validator.operation.OperationValidator;
import ru.market.domain.validator.operation.OperationValidatorImpl;

import ru.market.dto.operation.OperationBasedDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationResultDTO;
import ru.market.dto.operation.OperationTransferDTO;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class OperationExecutorImpl implements OperationExecutor {
    private OperationRepository operationRepository;
    private OperationConverter operationConverter;

    private BankAccountRepository bankAccountRepository;

    private CommonValidator<Operation> validator;

    public OperationExecutorImpl(OperationRepository operationRepository,
                                 OperationConverter operationConverter,
                                 BankAccountRepository bankAccountRepository) {

        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
        this.bankAccountRepository = bankAccountRepository;
        this.validator = validator();
    }

    @Transactional
    @Override
    public OperationResultDTO execute(OperationEnrollDebitDTO enrollDebitDTO, OperationType operationType,
                                      BiFunction<BankAccount, Operation, OperationResultDTO> function) {

        Operation operation = convertAndValidate(enrollDebitDTO);
        operation.setOperationType(operationType);

        BankAccount bankAccount = getBankAccountById(enrollDebitDTO.getAccountId());

        OperationResultDTO result = function.apply(bankAccount, operation);

        if(!result.isSuccess()){
            return result;
        }

        saveAndUpdate(bankAccount, operation);

        return result;
    }

    @Transactional
    @Override
    public OperationResultDTO execute(OperationTransferDTO transferDTO) {
        Operation operation = convertAndValidate(transferDTO);
        Operation debitOperation = operation.customClone();
        Operation enrollOperation = operation.customClone();

        BankAccount fromAccount = getBankAccountById(transferDTO.getFromAccountId());
        BankAccount toAccount = getBankAccountById(transferDTO.getToAccountId());

        OperationResultDTO result = OperationHelper.debit(fromAccount, debitOperation);

        if(!result.isSuccess()){
            return result;
        }

        OperationHelper.enrollment(toAccount, enrollOperation);

        prepareToSave(debitOperation, enrollOperation, fromAccount, toAccount);

        return new OperationResultDTO(true, "Перевод выполнен");
    }

    private Operation convertAndValidate(OperationBasedDTO basedDTO){
        Operation operation = operationConverter.convertToEntity(basedDTO);
        validator.validate(operation);
        return operation;
    }

    private CommonValidator<Operation> validator() {
        return operation -> {
            OperationValidator operationValidator = new OperationValidatorImpl();
            operationValidator.validateAmount(operation);
        };
    }

    private BankAccount getBankAccountById(Long accountId){
        return bankAccountRepository
                .findById(accountId).orElseThrow(
                        () -> new NotFoundException("Account with id " + accountId + " not found"));
    }

    private void prepareToSave(Operation debitOperation, Operation enrollOperation,
                               BankAccount fromAccount, BankAccount toAccount) {

        Person fromAccountPerson = fromAccount.getPerson();
        Person toAccountPerson = toAccount.getPerson();

        debitOperation.setOperationType(OperationType.DEBIT);
        debitOperation.setDescription(transferDescription(fromAccountPerson, toAccountPerson,
                () -> "Перевод средств на счет ID: " + toAccount.getId(),
                () -> String.format("Перевод средств (%s %s %s)",
                        toAccountPerson.getLastName(),
                        toAccountPerson.getFirstName(),
                        toAccountPerson.getMiddleName())
        ));

        saveAndUpdate(fromAccount, debitOperation);

        enrollOperation.setOperationType(OperationType.ENROLLMENT);
        enrollOperation.setDescription(transferDescription(fromAccountPerson, toAccountPerson,
                () -> "Зачисление средств со счета ID: " + fromAccount.getId(),
                () -> String.format("Зачисление средств от (%s %s %s)",
                        fromAccountPerson.getLastName(),
                        fromAccountPerson.getFirstName(),
                        fromAccountPerson.getMiddleName())
        ));

        saveAndUpdate(toAccount, enrollOperation);
    }

    private String transferDescription(Person fromAccountPerson, Person toAccountPerson,
                                       Supplier<String> supplierWithEqualPerson,
                                       Supplier<String> supplierWithNotEqualPerson){

        if(fromAccountPerson.equals(toAccountPerson)){
            return supplierWithEqualPerson.get();
        }

        return supplierWithNotEqualPerson.get();
    }

    private void saveAndUpdate(BankAccount bankAccount, Operation operation){
        bankAccountRepository.saveAndFlush(bankAccount);
        operation.setBankAccount(bankAccount);
        operationRepository.saveAndFlush(operation);
    }
}
