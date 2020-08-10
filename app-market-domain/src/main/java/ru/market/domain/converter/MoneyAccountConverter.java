package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.domain.data.MoneyAccount;
import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;

public class MoneyAccountConverter extends AbstractDefaultConverter<MoneyAccount, MoneyAccountNoIdDTO, MoneyAccountDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoneyAccountConverter.class);

    public MoneyAccountConverter(DozerBeanMapper mapper) {
        super(mapper, MoneyAccount.class, MoneyAccountNoIdDTO.class, MoneyAccountDTO.class);
    }

    @Override
    public MoneyAccountDTO convertToDTO(MoneyAccount moneyAccount) {
        if(moneyAccount == null){
            LOGGER.warn("Конвертирование в MoneyAccountDTO невозможно, входной параметр не задан.");
            return null;
        }

        MoneyAccountDTO bankAccountDTO =  super.convertToDTO(moneyAccount);
        bankAccountDTO.setDateCreatedStr(DateTimeConverter.convertToDateStr(moneyAccount.getDateCreated()));
        LOGGER.debug("Finish convertToDTO [extra]. DTO = {}", moneyAccount);

        return bankAccountDTO;
    }
}
