package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.domain.data.Operation;

import ru.market.dto.operation.OperationBasedDTO;
import ru.market.dto.operation.OperationDTO;

public class OperationConverter extends AbstractDefaultConverter<Operation, OperationBasedDTO, OperationDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationConverter.class);

    public OperationConverter(DozerBeanMapper mapper) {
        super(mapper, Operation.class, OperationBasedDTO.class, OperationDTO.class);
    }

    @Override
    public OperationBasedDTO convertToBasedDTO(Operation operation) {
        throw new UnsupportedOperationException("OperationBasedDTO is abstract class");
    }

    @Override
    public OperationDTO convertToDTO(Operation operation) {
        if(operation == null){
            LOGGER.warn("Конвертирование в OperationDTO невозможно, входной параметр не задан.");
            return null;
        }

        OperationDTO operationDTO = super.convertToDTO(operation);
        operationDTO.setDateStr(DateTimeConverter.convertToDateStr(operation.getDate()));
        operationDTO.setTimeStr(DateTimeConverter.convertToTimeStr(operation.getTime()));
        LOGGER.debug("Finish convertToDTO [extra]. DTO = {}", operationDTO);

        return operationDTO;
    }

    @Override
    public Operation convertToEntity(OperationBasedDTO operationBasedDTO) {
        if(operationBasedDTO == null){
            LOGGER.warn("Конвертирование в Operation невозможно, входной параметр не задан.");
            return null;
        }

        Operation operation = super.convertToEntity(operationBasedDTO);
        operation.setDate(DateTimeConverter.convertToLocalDate(operationBasedDTO.getDateStr()));
        operation.setTime(DateTimeConverter.convertToLocalTime(operationBasedDTO.getTimeStr()));
        LOGGER.debug("Finish convertToEntity [extra]. Entity = {}", operation);

        return operation;
    }
}
