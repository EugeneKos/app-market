package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;

import ru.market.domain.data.BankAccount;

import ru.market.dto.bank.BankAccountDTO;
import ru.market.dto.bank.BankAccountNoIdDTO;

public class BankAccountConverter extends AbstractAccountConverter<BankAccount, BankAccountNoIdDTO, BankAccountDTO> {
    public BankAccountConverter(DozerBeanMapper mapper) {
        super(mapper, BankAccount.class, BankAccountNoIdDTO.class, BankAccountDTO.class);
    }
}
