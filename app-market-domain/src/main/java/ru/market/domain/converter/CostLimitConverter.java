package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.domain.data.CostLimit;
import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitInfoDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

public class CostLimitConverter extends AbstractDefaultConverter<CostLimit, CostLimitNoIdDTO, CostLimitDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CostLimitConverter.class);

    private DozerBeanMapper mapper;

    public CostLimitConverter(DozerBeanMapper mapper){
        super(mapper, CostLimit.class, CostLimitNoIdDTO.class, CostLimitDTO.class);
        this.mapper = mapper;
    }

    @Override
    public CostLimitNoIdDTO convertToBasedDTO(CostLimit costLimit) {
        if(costLimit == null){
            LOGGER.warn("Конвертирование в CostLimitNoIdDTO невозможно, входной параметр не задан.");
            return null;
        }

        CostLimitNoIdDTO costLimitNoIdDTO = super.convertToBasedDTO(costLimit);
        costLimitNoIdDTO.setBeginDateStr(DateTimeConverter.convertToDateStr(costLimit.getBeginDate()));
        costLimitNoIdDTO.setEndDateStr(DateTimeConverter.convertToDateStr(costLimit.getEndDate()));
        LOGGER.debug("Finish convertToBasedDTO [extra]. BasedDTO = {}", costLimitNoIdDTO);

        return costLimitNoIdDTO;
    }

    @Override
    public CostLimitDTO convertToDTO(CostLimit costLimit) {
        if(costLimit == null){
            LOGGER.warn("Конвертирование в CostLimitDTO невозможно, входной параметр не задан.");
            return null;
        }

        CostLimitDTO costLimitDTO = super.convertToDTO(costLimit);
        costLimitDTO.setBeginDateStr(DateTimeConverter.convertToDateStr(costLimit.getBeginDate()));
        costLimitDTO.setEndDateStr(DateTimeConverter.convertToDateStr(costLimit.getEndDate()));
        LOGGER.debug("Finish convertToDTO [extra]. DTO = {}", costLimitDTO);

        return costLimitDTO;
    }

    public CostLimitInfoDTO convertToCostLimitInfoDTO(CostLimit costLimit){
        if(costLimit == null){
            LOGGER.warn("Конвертирование в CostLimitInfoDTO невозможно, входной параметр не задан.");
            return null;
        }

        LOGGER.debug("Start convertToCostLimitInfoDTO. CostLimit = {}", costLimit);
        CostLimitInfoDTO costLimitInfoDTO = mapper.map(costLimit, CostLimitInfoDTO.class);
        costLimitInfoDTO.setBeginDateStr(DateTimeConverter.convertToDateStr(costLimit.getBeginDate()));
        costLimitInfoDTO.setEndDateStr(DateTimeConverter.convertToDateStr(costLimit.getEndDate()));
        LOGGER.debug("Finish convertToCostLimitInfoDTO. CostLimitInfoDTO = {}", costLimitInfoDTO);

        return costLimitInfoDTO;
    }

    @Override
    public CostLimit convertToEntity(CostLimitNoIdDTO costLimitNoIdDTO) {
        if(costLimitNoIdDTO == null){
            LOGGER.warn("Конвертирование в CostLimit невозможно, входной параметр не задан.");
            return null;
        }

        CostLimit costLimit = super.convertToEntity(costLimitNoIdDTO);
        costLimit.setBeginDate(DateTimeConverter.convertToLocalDate(costLimitNoIdDTO.getBeginDateStr()));
        costLimit.setEndDate(DateTimeConverter.convertToLocalDate(costLimitNoIdDTO.getEndDateStr()));
        LOGGER.debug("Finish convertToEntity [extra]. Entity = {}", costLimit);

        return costLimit;
    }
}
