package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDefaultConverter<Entity, BasedDTO, DTO extends BasedDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDefaultConverter.class);

    private DozerBeanMapper mapper;

    private Class<Entity> domainClass;
    private Class<BasedDTO> basedDtoClass;
    private Class<DTO> dtoClass;

    AbstractDefaultConverter(DozerBeanMapper mapper, Class<Entity> domainClass, Class<BasedDTO> basedDtoClass, Class<DTO> dtoClass) {
        this.mapper = mapper;
        this.domainClass = domainClass;
        this.basedDtoClass = basedDtoClass;
        this.dtoClass = dtoClass;
    }

    public BasedDTO convertToBasedDTO(Entity entity){
        if(entity == null){
            LOGGER.warn("Конвертирование в BasedDTO невозможно, входной параметр не задан. {}", basedDtoClass);
            return null;
        }

        LOGGER.debug("Start convertToBasedDTO. Entity = {}", entity);
        BasedDTO basedDTO = mapper.map(entity, basedDtoClass);
        LOGGER.debug("Finish convertToBasedDTO. BasedDTO = {}", basedDTO);

        return basedDTO;
    }

    public DTO convertToDTO(Entity entity) {
        if(entity == null){
            LOGGER.warn("Конвертирование в Entity невозможно, входной параметр не задан. {}", dtoClass);
            return null;
        }

        LOGGER.debug("Start convertToDTO. Entity = {}", entity);
        DTO dto = mapper.map(entity, dtoClass);
        LOGGER.debug("Finish convertToDTO. DTO = {}", dto);

        return dto;
    }

    public Entity convertToEntity(BasedDTO dto) {
        if(dto == null){
            LOGGER.warn("Конвертирование в DTO невозможно, входной параметр не задан. {}", domainClass);
            return null;
        }

        LOGGER.debug("Start convertToEntity. BasedDTO = {}", dto);
        Entity entity = mapper.map(dto, domainClass);
        LOGGER.debug("Finish convertToEntity. Entity = {}", entity);

        return entity;
    }
}
