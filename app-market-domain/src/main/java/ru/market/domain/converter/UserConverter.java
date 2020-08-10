package ru.market.domain.converter;

import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.domain.data.User;

import ru.market.dto.person.PersonNoIdDTO;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserAdditionalDTO;

import java.time.LocalDateTime;
import java.util.Collections;

public class UserConverter extends AbstractDefaultConverter<User, UserDTO, UserAdditionalDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserConverter.class);

    private static final String TIMESTAMP_STATUS_FIELD_NAME = "timestampStatus";

    private DozerBeanMapper mapper;

    public UserConverter(DozerBeanMapper mapper) {
        super(mapper, User.class, UserDTO.class, UserAdditionalDTO.class);
        configure(mapper);
        this.mapper = mapper;
    }

    public UserAdditionalDTO convertToUserAdditionalDTO(RegistrationDTO registrationDTO){
        if(registrationDTO == null){
            LOGGER.warn("Конвертирование в UserAdditionalDTO невозможно, входной параметр не задан.");
            return null;
        }

        LOGGER.debug("Start convertToUserAdditionalDTO. RegistrationDTO = {}", registrationDTO);
        UserAdditionalDTO userAdditionalDTO = mapper.map(registrationDTO, UserAdditionalDTO.class);
        LOGGER.debug("Finish convertToUserAdditionalDTO. UserAdditionalDTO = {}", userAdditionalDTO);

        return userAdditionalDTO;
    }

    public PersonNoIdDTO convertToPersonNoIdDTO(RegistrationDTO registrationDTO){
        if(registrationDTO == null){
            LOGGER.warn("Конвертирование в PersonNoIdDTO невозможно, входной параметр не задан.");
            return null;
        }

        LOGGER.debug("Start convertToPersonNoIdDTO. RegistrationDTO = {}", registrationDTO);
        PersonNoIdDTO personNoIdDTO = mapper.map(registrationDTO, PersonNoIdDTO.class);
        LOGGER.debug("Finish convertToPersonNoIdDTO. PersonNoIdDTO = {}", personNoIdDTO);

        return personNoIdDTO;
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

    private static class LocalDateTimeConverter implements CustomConverter {
        @Override
        public Object convert(Object dest, Object source, Class<?> destinationClass, Class<?> sourceClass) {
            if(source == null){
                LOGGER.warn("LocalDateTimeConverter. Object source is null.");
                return null;
            }

            if(source instanceof LocalDateTime){
                LocalDateTime dateTime = (LocalDateTime) source;
                return LocalDateTime.from(dateTime);
            }

            LOGGER.warn("LocalDateTimeConverter. Object source is not LocalDateTime. Object source class: {}", source.getClass());
            return null;
        }
    }
}
