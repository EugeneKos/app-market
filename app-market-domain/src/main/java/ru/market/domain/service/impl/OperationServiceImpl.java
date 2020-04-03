package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.OperationConverter;
import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.enumeration.OperationType;
import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.repository.common.OperationRepository;
import ru.market.domain.service.IOperationService;
import ru.market.domain.service.utils.OperationHelper;

import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationResultDTO;
import ru.market.dto.operation.OperationTransferDTO;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class OperationServiceImpl implements IOperationService {
    private OperationRepository operationRepository;
    private OperationConverter operationConverter;

    private BankAccountRepository bankAccountRepository;

    public OperationServiceImpl(OperationRepository operationRepository,
                                OperationConverter operationConverter,
                                BankAccountRepository bankAccountRepository) {

        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
        this.bankAccountRepository = bankAccountRepository;
    }

    private OperationResultDTO execute(OperationEnrollDebitDTO enrollDebitDTO,
                                       OperationType operationType,
                                       BiFunction<BankAccount, Operation, OperationResultDTO> process){

        if(enrollDebitDTO == null){
            return new OperationResultDTO(false, "Operation is null");
        }

        Long accountId = enrollDebitDTO.getAccountId();
        if(accountId == null){
            throw new NullPointerException("Account id in operation is null");
        }

        Operation operation = operationConverter.convertToEntity(enrollDebitDTO);
        operation.setOperationType(operationType);

        BankAccount bankAccount = bankAccountRepository.getOne(accountId);

        OperationResultDTO resultDTO = process.apply(bankAccount, operation);

        if(!resultDTO.isSuccess()){
            return resultDTO;
        }

        bankAccountRepository.saveAndFlush(bankAccount);
        operation.setBankAccount(bankAccount);
        operationRepository.saveAndFlush(operation);

        return resultDTO;
    }

    @Transactional
    @Override
    public OperationResultDTO enrollment(OperationEnrollDebitDTO enrollDebitDTO) {
        return execute(enrollDebitDTO, OperationType.ENROLLMENT, OperationHelper::enrollment);
    }

    @Transactional
    @Override
    public OperationResultDTO debit(OperationEnrollDebitDTO enrollDebitDTO) {
        return execute(enrollDebitDTO, OperationType.DEBIT, OperationHelper::debit);
    }

    @Transactional
    @Override
    public OperationResultDTO transfer(OperationTransferDTO transferDTO) {
        if(transferDTO == null){
            return new OperationResultDTO(false, "Operation is null");
        }

        Long fromAccountId = transferDTO.getFromAccountId();
        Long toAccountId = transferDTO.getToAccountId();

        OperationEnrollDebitDTO enrollDebitDTO = convertToEnrollDebitFromTransfer(transferDTO);

        enrollDebitDTO.setAccountId(fromAccountId);
        enrollDebitDTO.setDescription("Перевод средств на счет ID: " + toAccountId);
        OperationResultDTO resultDTO = execute(enrollDebitDTO, OperationType.DEBIT, OperationHelper::debit);

        if(!resultDTO.isSuccess()){
            return new OperationResultDTO(false, "Ошибка перевода. " + resultDTO.getDescription());
        }

        enrollDebitDTO.setAccountId(toAccountId);
        enrollDebitDTO.setDescription("Зачисление средств со счета ID: " + fromAccountId);
        execute(enrollDebitDTO, OperationType.ENROLLMENT, OperationHelper::enrollment);

        return new OperationResultDTO(true, "Перевод выполнен");
    }

    private OperationEnrollDebitDTO convertToEnrollDebitFromTransfer(OperationTransferDTO transferDTO){
        OperationEnrollDebitDTO enrollDebitDTO = new OperationEnrollDebitDTO();
        enrollDebitDTO.setDateStr(transferDTO.getDateStr());
        enrollDebitDTO.setTimeStr(transferDTO.getTimeStr());
        enrollDebitDTO.setAmount(transferDTO.getAmount());
        return enrollDebitDTO;
    }

    @Override
    public Set<OperationDTO> getAllByAccountId(Long accountId) {
        return operationRepository.findAllByAccountId(accountId)
                .stream()
                .map(operationConverter::convertToDTO)
                .collect(Collectors.toSet());
    }
}
