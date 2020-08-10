package ru.market.domain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.MoneyAccountConverter;
import ru.market.domain.data.MoneyAccount;
import ru.market.domain.exception.BadRequestException;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(MoneyAccountServiceImpl.class);

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
        LOGGER.info("Создание денежного счета");
        if(moneyAccountNoIdDTO == null){
            throw new BadRequestException("Данные для создания денежного счета не заданы");
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
        LOGGER.info("Обновление денежного счета. MoneyAccountId = {}", moneyAccount.getId());
        moneyAccountRepository.saveAndFlush(moneyAccount);
    }

    @Override
    public MoneyAccount getMoneyAccountById(Long id) {
        LOGGER.info("Получение денежного счета по id = {}", id);
        return moneyAccountRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Money account with id %d not found", id))
        );
    }

    @Override
    public MoneyAccountDTO getById(Long id) {
        MoneyAccount moneyAccount = getMoneyAccountById(id);
        return moneyAccountConverter.convertToDTO(moneyAccount);
    }

    @Override
    public Set<MoneyAccountDTO> getAll() {
        LOGGER.info("Получение всех денежных счетов");
        return moneyAccountRepository.findAllByPersonId(personProvider.getCurrentPersonId()).stream()
                .map(moneyAccountConverter::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Long> getAllIdByPersonId(Long personId) {
        LOGGER.info("Получение всех id денежных счетов по personId = {}", personId);
        return moneyAccountRepository.findAllIdByPersonId(personId);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        LOGGER.info("Удаление денежного счета по id = {}", id);
        moneyAccountRepository.deleteById(id);
        MoneyAccountLockHolder.removeMoneyAccountLock(id);
    }
}
