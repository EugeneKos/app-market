package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;

import ru.market.domain.data.BankAccount;
import ru.market.dto.bank.BankAccountNoIdDTO;

public abstract class AbstractAccountConverter<Entity extends BankAccount, BasedDTO extends BankAccountNoIdDTO, DTO extends BasedDTO>
        extends AbstractDefaultConverter<Entity, BasedDTO, DTO> {

    AbstractAccountConverter(DozerBeanMapper mapper, Class<Entity> domainClass, Class<BasedDTO> basedDtoClass, Class<DTO> dtoClass) {
        super(mapper, domainClass, basedDtoClass, dtoClass);
    }

    @Override
    public DTO convertToDTO(Entity bankAccount) {
        if(bankAccount == null){
            return null;
        }

        DTO bankAccountDTO =  super.convertToDTO(bankAccount);
        bankAccountDTO.setDateCreatedStr(DateTimeConverter.convertToDateStr(bankAccount.getDateCreated()));

        return bankAccountDTO;
    }
}
