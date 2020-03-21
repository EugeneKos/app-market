package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.CashConverter;
import ru.market.domain.data.Cash;
import ru.market.domain.exception.MustIdException;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.repository.CashRepository;
import ru.market.domain.service.ICashService;
import ru.market.domain.service.IPersonProvider;
import ru.market.dto.cash.CashDTO;
import ru.market.dto.cash.CashNoIdDTO;

import java.util.Set;
import java.util.stream.Collectors;

public class CashServiceImpl implements ICashService {
    private CashRepository cashRepository;
    private CashConverter cashConverter;

    private IPersonProvider personProvider;

    public CashServiceImpl(CashRepository cashRepository, CashConverter cashConverter) {
        this.cashRepository = cashRepository;
        this.cashConverter = cashConverter;
    }

    public void setPersonProvider(IPersonProvider personProvider) {
        this.personProvider = personProvider;
    }

    @Transactional
    @Override
    public CashDTO create(CashNoIdDTO cashNoIdDTO) {
        return update(cashNoIdDTO, false);
    }

    @Transactional
    @Override
    public CashDTO update(CashDTO cashDTO) {
        return update(cashDTO, true);
    }

    private CashDTO update(CashNoIdDTO cashDTO, boolean isMustId){
        if(cashDTO == null){
            return null;
        }

        Cash cash = cashConverter.convertToCash(cashDTO);
        if(isMustId && cash.getId() == null){
            throw new MustIdException("Cash id should be given");
        }

        assertExistById(cash);
        assertUniqueByName(cash);

        cash.setPerson(personProvider.getCurrentPerson());

        cash = cashRepository.saveAndFlush(cash);
        return cashConverter.convertToCashDTO(cash);
    }

    private void assertExistById(Cash cash){
        if(cash.getId() == null){
            return;
        }

        cashRepository.findById(cash.getId()).orElseThrow(
                () -> new NotFoundException("Cash with id " + cash.getId() + " not found")
        );
    }

    private void assertUniqueByName(Cash cash){
        Cash founded = cashRepository.findByName(cash.getName());
        if(founded != null && !founded.getId().equals(cash.getId())){
            throw new NotUniqueException("Cash with name: " + founded.getName() + " already exist");
        }
    }

    @Override
    public CashDTO getById(Long id) {
        Cash cash = cashRepository.findById(id).orElse(null);
        return cashConverter.convertToCashDTO(cash);
    }

    @Override
    public Set<CashDTO> getAll() {
        return cashRepository.findAllByPerson(personProvider.getCurrentPerson()).stream()
                .map(cashConverter::convertToCashDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Long> getAllCashIdByPersonId(Long personId) {
        return cashRepository.findAllCashIdByPersonId(personId);
    }

    @Override
    public void deleteById(Long id) {
        cashRepository.deleteById(id);
    }

    @Override
    public void deleteAllByPersonId(Long personId) {
        cashRepository.deleteByPersonId(personId);
    }
}
