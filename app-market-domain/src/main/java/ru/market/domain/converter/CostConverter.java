package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;

import ru.market.domain.data.Cost;
import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;

public class CostConverter extends AbstractDefaultConverter<Cost, CostNoIdDTO, CostDTO> {
    private DozerBeanMapper mapper;

    public CostConverter(DozerBeanMapper mapper) {
        super(mapper, Cost.class, CostNoIdDTO.class, CostDTO.class);
        this.mapper = mapper;
    }

    @Override
    public CostDTO convertToDTO(Cost cost) {
        if(cost == null){
            return null;
        }

        CostDTO costDTO =  super.convertToDTO(cost);

        costDTO.setDateStr(DateTimeConverter.convertToDateStr(cost.getDate()));
        costDTO.setTimeStr(DateTimeConverter.convertToTimeStr(cost.getTime()));

        return costDTO;
    }

    @Override
    public Cost convertToEntity(CostNoIdDTO costNoIdDTO) {
        if(costNoIdDTO == null){
            return null;
        }

        Cost cost = super.convertToEntity(costNoIdDTO);

        cost.setDate(DateTimeConverter.convertToLocalDate(costNoIdDTO.getDateStr()));
        cost.setTime(DateTimeConverter.convertToLocalTime(costNoIdDTO.getTimeStr()));

        return cost;
    }

    public OperationEnrollDebitDTO convertToOperationEnrollDebitDTO(CostNoIdDTO costNoIdDTO){
        OperationEnrollDebitDTO enrollDebitDTO = mapper.map(costNoIdDTO, OperationEnrollDebitDTO.class);
        enrollDebitDTO.setAmount(costNoIdDTO.getSum());
        return enrollDebitDTO;
    }
}
