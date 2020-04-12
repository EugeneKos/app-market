package ru.market.domain.service.operation.impl;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.Person;
import ru.market.domain.data.enumeration.OperationType;

import ru.market.domain.service.operation.OperationAdditionalServices;
import ru.market.domain.service.operation.OperationExecutor;
import ru.market.domain.service.utils.OperationHelper;

import ru.market.dto.operation.OperationResultDTO;

import java.util.function.Supplier;

public class TransferOperationExecutor extends AbstractOperationExecutor implements OperationExecutor {
    private final BankAccount fromAccount;
    private final BankAccount toAccount;

    private Operation operation;

    TransferOperationExecutor(OperationAdditionalServices operationAdditionalServices,
                              BankAccount fromAccount, BankAccount toAccount, Operation operation) {

        super(operationAdditionalServices);

        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.operation = operation;
    }

    public OperationResultDTO execute(){
        Long fromAccountId = fromAccount.getId();
        Long toAccountId = toAccount.getId();

        if(fromAccountId < toAccountId){
            synchronized (fromAccount){
                synchronized (toAccount){
                    return transfer();
                }
            }
        } else {
            synchronized (toAccount){
                synchronized (fromAccount){
                    return transfer();
                }
            }
        }
    }

    private OperationResultDTO transfer(){
        OperationResultDTO result = OperationHelper.debit(fromAccount, operation);

        if(!result.isSuccess()){
            return result;
        }

        OperationHelper.enrollment(toAccount, operation);

        prepareToSave();

        return new OperationResultDTO(true, "Перевод выполнен");
    }

    private void prepareToSave() {
        Person fromAccountPerson = fromAccount.getPerson();
        Person toAccountPerson = toAccount.getPerson();

        Operation debitOperation = operation.customClone();
        debitOperation.setOperationType(OperationType.DEBIT);
        debitOperation.setDescription(transferDescription(fromAccountPerson, toAccountPerson,
                () -> "Перевод средств на счет ID: " + toAccount.getId(),
                () -> String.format("Перевод средств (%s %s %s)",
                        toAccountPerson.getLastName(),
                        toAccountPerson.getFirstName(),
                        toAccountPerson.getMiddleName())
        ));

        saveAndUpdate(fromAccount, debitOperation);

        Operation enrollOperation = operation.customClone();
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
}
