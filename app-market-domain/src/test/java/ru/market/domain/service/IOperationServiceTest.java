package ru.market.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.domain.data.Operation;
import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;
import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationFilterDTO;
import ru.market.dto.operation.OperationTransferDTO;

import java.math.BigDecimal;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:database-test-config.properties")
public class IOperationServiceTest {
    @Autowired
    private IOperationService operationService;

    @Autowired
    private IMoneyAccountService moneyAccountService;

    @Test
    public void moneyAccountServiceTest(){
        Long moneyAccountId1 = getMoneyAccountId("5000", "Test money account 1", "Test money account 1");
        Long moneyAccountId2 = getMoneyAccountId("7000", "Test money account 2", "Test money account 2");

        OperationDTO enrollOperation = enrollmentTest(moneyAccountId1);
        OperationDTO debitOperation = debitTest(moneyAccountId2);
        OperationDTO transferOperation = transferTest(moneyAccountId1, moneyAccountId2);
        getOperationByIdTest(transferOperation);
        rollbackTest(debitOperation);
        updateTest(enrollOperation);
        getAllByMoneyAccountIdTest(moneyAccountId1);
        getAllByMoneyAccountIdAndFilterTest(moneyAccountId1);
    }

    private Long getMoneyAccountId(String balance, String name, String description){
        MoneyAccountNoIdDTO dto = MoneyAccountNoIdDTO.builder()
                .balance(balance)
                .name(name)
                .description(description)
                .build();

        MoneyAccountDTO moneyAccountDTO = moneyAccountService.create(dto);

        return moneyAccountDTO.getId();
    }

    private OperationDTO enrollmentTest(Long moneyAccountId){
        OperationEnrollDebitDTO enrollDebitDTO = OperationEnrollDebitDTO.operationEnrollBuilder()
                .moneyAccountId(moneyAccountId)
                .description("Тест операции зачисление")
                .build();

        enrollDebitDTO.setAmount("200");

        System.out.println("---------- enrollment ----------");
        OperationDTO operationDTO = operationService.enrollment(enrollDebitDTO);
        System.out.println("--------------------------------");

        Assert.assertNotNull(operationDTO);

        return operationDTO;
    }

    private OperationDTO debitTest(Long moneyAccountId){
        OperationEnrollDebitDTO enrollDebitDTO = OperationEnrollDebitDTO.operationEnrollBuilder()
                .moneyAccountId(moneyAccountId)
                .description("Тест операции списание")
                .build();

        enrollDebitDTO.setAmount("300");

        System.out.println("---------- debit ----------");
        OperationDTO operationDTO = operationService.debit(enrollDebitDTO);
        System.out.println("---------------------------");

        Assert.assertNotNull(operationDTO);

        return operationDTO;
    }

    private OperationDTO transferTest(Long fromMoneyAccountId, Long toMoneyAccountId){
        OperationTransferDTO transferDTO = OperationTransferDTO.operationTransferBuilder()
                .fromMoneyAccountId(fromMoneyAccountId)
                .toMoneyAccountId(toMoneyAccountId)
                .build();

        transferDTO.setAmount("500");

        System.out.println("---------- transfer ----------");
        OperationDTO operationDTO = operationService.transfer(transferDTO);
        System.out.println("------------------------------");

        Assert.assertNotNull(operationDTO);

        return operationDTO;
    }

    private void getOperationByIdTest(OperationDTO dto){
        System.out.println("---------- getOperationById ----------");
        Operation operation = operationService.getOperationById(dto.getId());
        System.out.println("--------------------------------------");

        Assert.assertNotNull(operation);
    }

    private void rollbackTest(OperationDTO dto){
        Operation operation = operationService.getOperationById(dto.getId());

        System.out.println("---------- rollback ----------");
        operationService.rollback(operation);
        System.out.println("------------------------------");
    }

    private void updateTest(OperationDTO dto){
        Operation operation = operationService.getOperationById(dto.getId());
        operation.setAmount(new BigDecimal(1000));
        operation.setDescription("Измененная операция");

        System.out.println("---------- update ----------");
        operationService.update(operation);
        System.out.println("----------------------------");
    }

    private void getAllByMoneyAccountIdTest(Long moneyAccountId){
        System.out.println("---------- getAllByMoneyAccountId ----------");
        Set<OperationDTO> operations = operationService.getAllByMoneyAccountId(moneyAccountId);
        System.out.println("--------------------------------------------");

        Assert.assertNotNull(operations);
    }

    private void getAllByMoneyAccountIdAndFilterTest(Long moneyAccountId){
        OperationFilterDTO filter = new OperationFilterDTO();
        filter.setAmount(">200");

        System.out.println("---------- getAllByMoneyAccountIdAndFilter ----------");
        Set<OperationDTO> operations = operationService.getAllByMoneyAccountIdAndFilter(moneyAccountId, filter);
        System.out.println("-----------------------------------------------------");

        Assert.assertNotNull(operations);
    }
}