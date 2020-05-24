package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;

import ru.market.domain.data.MoneyAccount;
import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;

public class MoneyAccountConverter extends AbstractDefaultConverter<MoneyAccount, MoneyAccountNoIdDTO, MoneyAccountDTO> {
    public MoneyAccountConverter(DozerBeanMapper mapper) {
        super(mapper, MoneyAccount.class, MoneyAccountNoIdDTO.class, MoneyAccountDTO.class);
    }

    @Override
    public MoneyAccountDTO convertToDTO(MoneyAccount moneyAccount) {
        if(moneyAccount == null){
            return null;
        }

        MoneyAccountDTO bankAccountDTO =  super.convertToDTO(moneyAccount);
        bankAccountDTO.setDateCreatedStr(DateTimeConverter.convertToDateStr(moneyAccount.getDateCreated()));

        return bankAccountDTO;
    }
}
