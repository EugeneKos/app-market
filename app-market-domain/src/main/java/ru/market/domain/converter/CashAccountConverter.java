package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;
import ru.market.domain.data.CashAccount;

import ru.market.dto.cash.CashAccountDTO;
import ru.market.dto.cash.CashAccountNoIdDTO;

public class CashAccountConverter extends AbstractAccountConverter<CashAccount, CashAccountNoIdDTO, CashAccountDTO> {
    private DozerBeanMapper mapper;

    public CashAccountConverter(DozerBeanMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CashAccountDTO convertToDTO(CashAccount cashAccount) {
        if(cashAccount == null){
            return null;
        }
        return mapper.map(cashAccount, CashAccountDTO.class);
    }

    @Override
    public CashAccount convertToEntity(CashAccountNoIdDTO cashAccountNoIdDTO) {
        if(cashAccountNoIdDTO == null){
            return null;
        }
        return mapper.map(cashAccountNoIdDTO, CashAccount.class);
    }
}
