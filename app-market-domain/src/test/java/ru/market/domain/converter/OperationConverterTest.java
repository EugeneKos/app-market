package ru.market.domain.converter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.ConverterConfiguration;
import ru.market.domain.data.Operation;
import ru.market.domain.data.enumeration.OperationType;
import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;

import java.time.LocalDate;
import java.time.LocalTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class OperationConverterTest {
    @Autowired
    private OperationConverter operationConverter;

    @Test
    public void convertToDTOTest(){
        Operation operation = new Operation();
        operation.setId(11L);
        operation.setDate(LocalDate.of(2020, 4, 3));
        operation.setTime(LocalTime.of(14, 31));
        operation.setAmount("250");
        operation.setDescription("op description");
        operation.setOperationType(OperationType.ENROLLMENT);

        OperationDTO dto = operationConverter.convertToDTO(operation);

        Assert.assertNotNull(dto);
        Assert.assertEquals(11L, dto.getId().longValue());
        Assert.assertEquals("03-04-2020", dto.getDateStr());
        Assert.assertEquals("14:31", dto.getTimeStr());
        Assert.assertEquals("250", dto.getAmount());
        Assert.assertEquals("op description", dto.getDescription());
        Assert.assertEquals("ENROLLMENT", dto.getOperationType());
    }

    @Test
    public void convertToEntityTest(){
        OperationEnrollDebitDTO enrollDebitDTO = new OperationEnrollDebitDTO();
        enrollDebitDTO.setDateStr("03-04-2020");
        enrollDebitDTO.setTimeStr("14:31");
        enrollDebitDTO.setAmount("250");
        enrollDebitDTO.setDescription("op description");

        Operation operation = operationConverter.convertToEntity(enrollDebitDTO);

        Assert.assertNotNull(operation);
        Assert.assertEquals(LocalDate.of(2020, 4, 3), operation.getDate());
        Assert.assertEquals(LocalTime.of(14, 31), operation.getTime());
        Assert.assertEquals("250", operation.getAmount());
        Assert.assertEquals("op description", operation.getDescription());
    }
}