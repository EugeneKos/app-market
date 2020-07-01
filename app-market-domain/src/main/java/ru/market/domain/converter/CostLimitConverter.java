package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;

import ru.market.domain.data.CostLimit;
import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitInfoDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

public class CostLimitConverter extends AbstractDefaultConverter<CostLimit, CostLimitNoIdDTO, CostLimitDTO> {
    private DozerBeanMapper mapper;

    public CostLimitConverter(DozerBeanMapper mapper){
        super(mapper, CostLimit.class, CostLimitNoIdDTO.class, CostLimitDTO.class);
        this.mapper = mapper;
    }

    @Override
    public CostLimitNoIdDTO convertToBasedDTO(CostLimit costLimit) {
        if(costLimit == null){
            return null;
        }

        CostLimitNoIdDTO costLimitNoIdDTO = super.convertToBasedDTO(costLimit);

        costLimitNoIdDTO.setBeginDateStr(DateTimeConverter.convertToDateStr(costLimit.getBeginDate()));
        costLimitNoIdDTO.setEndDateStr(DateTimeConverter.convertToDateStr(costLimit.getEndDate()));

        return costLimitNoIdDTO;
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

    public CostLimitInfoDTO convertToCostLimitInfoDTO(CostLimit costLimit){
        if(costLimit == null){
            return null;
        }

        CostLimitInfoDTO costLimitInfoDTO = mapper.map(costLimit, CostLimitInfoDTO.class);

        costLimitInfoDTO.setBeginDateStr(DateTimeConverter.convertToDateStr(costLimit.getBeginDate()));
        costLimitInfoDTO.setEndDateStr(DateTimeConverter.convertToDateStr(costLimit.getEndDate()));

        return costLimitInfoDTO;
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
