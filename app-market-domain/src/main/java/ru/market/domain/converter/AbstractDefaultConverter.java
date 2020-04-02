package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;

public abstract class AbstractDefaultConverter<Entity, BasedDTO, DTO extends BasedDTO> {
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
            return null;
        }
        return mapper.map(entity, basedDtoClass);
    }

    public DTO convertToDTO(Entity entity) {
        if(entity == null){
            return null;
        }
        return mapper.map(entity, dtoClass);
    }

    public Entity convertToEntity(BasedDTO dto) {
        if(dto == null){
            return null;
        }
        return mapper.map(dto, domainClass);
    }
}
