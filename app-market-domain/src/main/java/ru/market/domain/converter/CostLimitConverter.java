package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;

import ru.market.domain.data.CostLimit;
import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

public class CostLimitConverter extends AbstractDefaultConverter<CostLimit, CostLimitNoIdDTO, CostLimitDTO> {
    public CostLimitConverter(DozerBeanMapper mapper){
        super(mapper, CostLimit.class, CostLimitNoIdDTO.class, CostLimitDTO.class);
    }

    @Override
    public CostLimitDTO convertToDTO(CostLimit costLimit) {
        if(costLimit == null){
            return null;
        }

        CostLimitDTO costLimitDTO = super.convertToDTO(costLimit);

        costLimitDTO.setBeginDateStr(DateTimeConverter.convertToDateStr(costLimit.getBeginDate()));
        costLimitDTO.setEndDateStr(DateTimeConverter.convertToDateStr(costLimit.getEndDate()));

        return costLimitDTO;
    }

    @Override
    public CostLimit convertToEntity(CostLimitNoIdDTO costLimitNoIdDTO) {
        if(costLimitNoIdDTO == null){
            return null;
        }

        CostLimit costLimit = super.convertToEntity(costLimitNoIdDTO);

        costLimit.setBeginDate(DateTimeConverter.convertToLocalDate(costLimitNoIdDTO.getBeginDateStr()));
        costLimit.setEndDate(DateTimeConverter.convertToLocalDate(costLimitNoIdDTO.getEndDateStr()));

        return costLimit;
    }
}
