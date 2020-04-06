package ru.market.domain.service.operation.impl;

import ru.market.domain.converter.OperationConverter;
import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.Person;
import ru.market.domain.data.enumeration.OperationType;
import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.repository.common.OperationRepository;
import ru.market.domain.service.operation.OperationExecutor;
import ru.market.domain.service.utils.OperationHelper;

import ru.market.dto.operation.OperationResultDTO;
import ru.market.dto.operation.OperationTransferDTO;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class OperationTransferExecutor implements OperationExecutor {
    private OperationRepository operationRepository;
    private OperationConverter operationConverter;

    private OperationTransferDTO transferDTO;

    private BankAccountRepository bankAccountRepository;

    OperationTransferExecutor(OperationRepository operationRepository,
                                     OperationConverter operationConverter,
                                     OperationTransferDTO transferDTO,
                                     BankAccountRepository bankAccountRepository) {

        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
        this.transferDTO = transferDTO;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public OperationResultDTO execute() {
        BankAccount fromBankAccount = bankAccountRepository.getOne(transferDTO.getFromAccountId());
        BankAccount toBankAccount = bankAccountRepository.getOne(transferDTO.getToAccountId());

        Person fromAccountPerson = fromBankAccount.getPerson();
        Person toAccountPerson = toBankAccount.getPerson();

        OperationTransferDetail detail = execute(fromBankAccount, OperationType.DEBIT,
                transferDescription(
                        fromAccountPerson, toAccountPerson,
                        () -> "Перевод средств на счет ID: " + toBankAccount.getId(),
                        () -> String.format("Перевод средств (%s %s %s)",
                                toAccountPerson.getLastName(), toAccountPerson.getFirstName(), toAccountPerson.getMiddleName())
                ),
                OperationHelper::debit
        );

        OperationResultDTO detailResult = detail.getResult();

        if(!detailResult.isSuccess()){
            return new OperationResultDTO(false, "Ошибка перевода. " + detailResult.getDescription());
        }

        saveAndUpdate(detail.getOperation(), fromBankAccount);

        detail = execute(toBankAccount, OperationType.ENROLLMENT,
                transferDescription(fromAccountPerson, toAccountPerson,
                        () -> "Зачисление средств со счета ID: " + fromBankAccount.getId(),
                        () -> String.format("Зачисление средств от (%s %s %s)",
                                fromAccountPerson.getLastName(), fromAccountPerson.getFirstName(), fromAccountPerson.getMiddleName())
                ),
                OperationHelper::enrollment
        );

        saveAndUpdate(detail.getOperation(), toBankAccount);

        return new OperationResultDTO(true, "Перевод выполнен");
    }

    private OperationTransferDetail execute(BankAccount fromBankAccount, OperationType operationType,
                                            String operationDescription,
                                            BiFunction<BankAccount, Operation, OperationResultDTO> process){

        Operation operation = operationConverter.convertToEntity(transferDTO);
        operation.setOperationType(operationType);
        operation.setDescription(operationDescription);

        OperationResultDTO result = process.apply(fromBankAccount, operation);

        return new OperationTransferDetail(result, operation);
    }

    private String transferDescription(Person fromAccountPerson, Person toAccountPerson,
                                       Supplier<String> supplierWithEqualPerson,
                                       Supplier<String> supplierWithNotEqualPerson){

        if(fromAccountPerson.equals(toAccountPerson)){
            return supplierWithEqualPerson.get();
        }

        return supplierWithNotEqualPerson.get();
    }

    private void saveAndUpdate(Operation operation, BankAccount bankAccount){
        bankAccountRepository.saveAndFlush(bankAccount);
        operation.setBankAccount(bankAccount);
        operationRepository.saveAndFlush(operation);
    }

    private class OperationTransferDetail{
        private OperationResultDTO result;
        private Operation operation;

        OperationTransferDetail(OperationResultDTO result, Operation operation) {
            this.result = result;
            this.operation = operation;
        }

        OperationResultDTO getResult() {
            return result;
        }

        Operation getOperation() {
            return operation;
        }
    }
}
