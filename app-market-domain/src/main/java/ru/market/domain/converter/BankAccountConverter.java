package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;

import ru.market.domain.data.BankAccount;

import ru.market.dto.bank.BankAccountDTO;
import ru.market.dto.bank.BankAccountNoIdDTO;

public class BankAccountConverter extends AbstractAccountConverter<BankAccount, BankAccountNoIdDTO, BankAccountDTO> {
    private DozerBeanMapper mapper;

    public BankAccountConverter(DozerBeanMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BankAccountDTO convertToDTO(BankAccount entity) {
        if(entity == null){
            return null;
        }

        return mapper.map(entity, BankAccountDTO.class);
    }

    @Override
    public BankAccount convertToEntity(BankAccountNoIdDTO dto) {
        if(dto == null){
            return null;
        }

        return mapper.map(dto, BankAccount.class);
    }
}
