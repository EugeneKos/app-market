package ru.market.domain.converter;

import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import ru.market.domain.data.User;

import ru.market.dto.person.PersonNoIdDTO;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserAdditionalDTO;

import java.time.LocalDateTime;
import java.util.Collections;

public class UserConverter extends AbstractDefaultConverter<User, UserDTO, UserAdditionalDTO> {
    private static final String TIMESTAMP_STATUS_FIELD_NAME = "timestampStatus";

    private DozerBeanMapper mapper;

    public UserConverter(DozerBeanMapper mapper) {
        super(mapper, User.class, UserDTO.class, UserAdditionalDTO.class);
        configure(mapper);
        this.mapper = mapper;
    }

    public UserAdditionalDTO convertToUserAdditionalDTO(RegistrationDTO registrationDTO){
        if(registrationDTO == null){
            return null;
        }

        return mapper.map(registrationDTO, UserAdditionalDTO.class);
    }

    public PersonNoIdDTO convertToPersonNoIdDTO(RegistrationDTO registrationDTO){
        if(registrationDTO == null){
            return null;
        }

        return mapper.map(registrationDTO, PersonNoIdDTO.class);
    }

    private void configure(DozerBeanMapper mapper){
        mapper.setCustomConverters(Collections.singletonList(new LocalDateTimeConverter()));
        mapper.addMapping(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(User.class, UserAdditionalDTO.class)
                        .fields(TIMESTAMP_STATUS_FIELD_NAME, TIMESTAMP_STATUS_FIELD_NAME,
                                FieldsMappingOptions.customConverter(LocalDateTimeConverter.class)
                        );
            }
        });
    }

    private class LocalDateTimeConverter implements CustomConverter {
        @Override
        public Object convert(Object dest, Object source, Class<?> destinationClass, Class<?> sourceClass) {
            if(source == null){
                return null;
            }

            if(source instanceof LocalDateTime){
                LocalDateTime dateTime = (LocalDateTime) source;
                return LocalDateTime.from(dateTime);
            }

            return null;
        }
    }
}
