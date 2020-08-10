package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.domain.data.Cost;
import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;

public class CostConverter extends AbstractDefaultConverter<Cost, CostNoIdDTO, CostDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CostConverter.class);

    private DozerBeanMapper mapper;

    public CostConverter(DozerBeanMapper mapper) {
        super(mapper, Cost.class, CostNoIdDTO.class, CostDTO.class);
        this.mapper = mapper;
    }

    @Override
    public CostNoIdDTO convertToBasedDTO(Cost cost) {
        if(cost == null){
            LOGGER.warn("Конвертирование в CostNoIdDTO невозможно, входной параметр не задан.");
            return null;
        }

        CostNoIdDTO costNoIdDTO = super.convertToBasedDTO(cost);
        convertAdditionalField(cost, costNoIdDTO);
        LOGGER.debug("Finish convertToBasedDTO [extra]. BasedDTO = {}", costNoIdDTO);

        return costNoIdDTO;
    }

    @Override
    public CostDTO convertToDTO(Cost cost) {
        if(cost == null){
            LOGGER.warn("Конвертирование в CostDTO невозможно, входной параметр не задан.");
            return null;
        }

        CostDTO costDTO =  super.convertToDTO(cost);
        convertAdditionalField(cost, costDTO);
        LOGGER.debug("Finish convertToDTO [extra]. DTO = {}", costDTO);

        return costDTO;
    }

    private void convertAdditionalField(Cost cost, CostNoIdDTO dto){
        dto.setDateStr(DateTimeConverter.convertToDateStr(cost.getDate()));
        dto.setTimeStr(DateTimeConverter.convertToTimeStr(cost.getTime()));
        dto.setCostLimitId(cost.getCostLimit() != null ? cost.getCostLimit().getId() : null);

        if(cost.getOperation() != null && cost.getOperation().getMoneyAccount() != null){
            dto.setMoneyAccountId(cost.getOperation().getMoneyAccount().getId());
        }
    }

    @Override
    public Cost convertToEntity(CostNoIdDTO costNoIdDTO) {
        if(costNoIdDTO == null){
            LOGGER.warn("Конвертирование в Cost невозможно, входной параметр не задан.");
            return null;
        }

        Cost cost = super.convertToEntity(costNoIdDTO);
        cost.setDate(DateTimeConverter.convertToLocalDate(costNoIdDTO.getDateStr()));
        cost.setTime(DateTimeConverter.convertToLocalTime(costNoIdDTO.getTimeStr()));
        LOGGER.debug("Finish convertToEntity [extra]. Entity = {}", cost);

        return cost;
    }

    public OperationEnrollDebitDTO convertToOperationEnrollDebitDTO(CostNoIdDTO costNoIdDTO){
        LOGGER.debug("Start convertToOperationEnrollDebitDTO. CostNoIdDTO = {}", costNoIdDTO);
        OperationEnrollDebitDTO enrollDebitDTO = mapper.map(costNoIdDTO, OperationEnrollDebitDTO.class);
        enrollDebitDTO.setAmount(costNoIdDTO.getSum());
        LOGGER.debug("Finish convertToOperationEnrollDebitDTO. OperationEnrollDebitDTO = {}", enrollDebitDTO);
        return enrollDebitDTO;
    }
}
