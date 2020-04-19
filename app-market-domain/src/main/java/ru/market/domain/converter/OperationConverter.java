package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;

import ru.market.domain.data.Operation;

import ru.market.dto.operation.OperationBasedDTO;
import ru.market.dto.operation.OperationDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class OperationConverter extends AbstractDefaultConverter<Operation, OperationBasedDTO, OperationDTO> {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public OperationConverter(DozerBeanMapper mapper) {
        super(mapper, Operation.class, OperationBasedDTO.class, OperationDTO.class);
    }

    @Override
    public OperationDTO convertToDTO(Operation operation) {
        if(operation == null){
            return null;
        }

        OperationDTO operationDTO = super.convertToDTO(operation);

        operationDTO.setDateStr(convertToDateStr(operation.getDate()));
        operationDTO.setTimeStr(convertToTimeStr(operation.getTime()));

        return operationDTO;
    }

    @Override
    public Operation convertToEntity(OperationBasedDTO operationBasedDTO) {
        if(operationBasedDTO == null){
            return null;
        }

        Operation operation = super.convertToEntity(operationBasedDTO);

        operation.setDate(convertToLocalDate(operationBasedDTO.getDateStr()));
        operation.setTime(convertToLocalTime(operationBasedDTO.getTimeStr()));

        return operation;
    }

    private LocalDate convertToLocalDate(String dateStr){
        if(dateStr == null || "".equals(dateStr)){
            return LocalDate.now();
        }

        return LocalDate.parse(dateStr, dateFormatter);
    }

    private String convertToDateStr(LocalDate date){
        return date.format(dateFormatter);
    }

    private LocalTime convertToLocalTime(String timeStr){
        if(timeStr == null || "".equals(timeStr)){
            return LocalTime.now();
        }

        return LocalTime.parse(timeStr, timeFormatter);
    }

    private String convertToTimeStr(LocalTime time){
        return time.format(timeFormatter);
    }
}
