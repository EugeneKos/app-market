package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.MoneyAccountConverter;
import ru.market.domain.data.MoneyAccount;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.repository.MoneyAccountRepository;
import ru.market.domain.service.IMoneyAccountService;
import ru.market.domain.service.IPersonProvider;
import ru.market.domain.validator.CommonValidator;
import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;
import ru.market.utils.MoneyAccountLockHolder;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class MoneyAccountServiceImpl implements IMoneyAccountService {
    private MoneyAccountRepository moneyAccountRepository;
    private MoneyAccountConverter moneyAccountConverter;

    private CommonValidator<MoneyAccount> validator;

    private IPersonProvider personProvider;

    public MoneyAccountServiceImpl(MoneyAccountRepository moneyAccountRepository,
                                   MoneyAccountConverter moneyAccountConverter,
                                   CommonValidator<MoneyAccount> validator,
                                   IPersonProvider personProvider) {

        this.moneyAccountRepository = moneyAccountRepository;
        this.moneyAccountConverter = moneyAccountConverter;
        this.validator = validator;
        this.personProvider = personProvider;
    }

    @Transactional
    @Override
    public MoneyAccountDTO create(MoneyAccountNoIdDTO moneyAccountNoIdDTO) {
        if(moneyAccountNoIdDTO == null){
            return null;
        }

        MoneyAccount moneyAccount = moneyAccountConverter.convertToEntity(moneyAccountNoIdDTO);
        moneyAccount.setDateCreated(LocalDate.now());
        moneyAccount.setPerson(personProvider.getCurrentPerson());

        validator.validate(moneyAccount);

        moneyAccount = moneyAccountRepository.saveAndFlush(moneyAccount);
        return moneyAccountConverter.convertToDTO(moneyAccount);
    }

    @Transactional
    @Override
    public void update(MoneyAccount moneyAccount) {
        moneyAccountRepository.saveAndFlush(moneyAccount);
    }

    @Override
    public MoneyAccount getMoneyAccountById(Long id) {
        return moneyAccountRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Money account with id %d not found", id))
        );
    }

    @Override
    public MoneyAccountDTO getById(Long id) {
        MoneyAccount moneyAccount = moneyAccountRepository.findById(id).orElse(null);
        return moneyAccountConverter.convertToDTO(moneyAccount);
    }

    @Override
    public Set<MoneyAccountDTO> getAll() {
        return moneyAccountRepository.findAllByPerson(personProvider.getCurrentPerson()).stream()
                .map(moneyAccountConverter::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Long> getAllIdByPersonId(Long personId) {
        return moneyAccountRepository.findAllIdByPersonId(personId);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        moneyAccountRepository.deleteById(id);
        MoneyAccountLockHolder.removeMoneyAccountLock(id);
    }
}
