package ru.market.domain.service;

import ru.market.dto.bank.BankAccountDTO;
import ru.market.dto.bank.BankAccountNoIdDTO;

public interface IBankAccountService extends DefaultBankService<BankAccountNoIdDTO, BankAccountDTO> {

}
