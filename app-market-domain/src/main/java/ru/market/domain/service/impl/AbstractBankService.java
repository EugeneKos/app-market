package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.data.BankAccount;
import ru.market.domain.converter.AbstractBankConverter;
import ru.market.domain.exception.MustIdException;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.repository.AbstractBankRepository;
import ru.market.domain.service.IPersonProvider;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractBankService<Entity extends BankAccount, NoIdDTO, DTO extends NoIdDTO> {
    private AbstractBankRepository<Entity> abstractBankRepository;
    private AbstractBankConverter<Entity, NoIdDTO, DTO> abstractBankConverter;

    private IPersonProvider personProvider;

    AbstractBankService(AbstractBankRepository<Entity> abstractBankRepository,
                               AbstractBankConverter<Entity, NoIdDTO, DTO> abstractBankConverter,
                               IPersonProvider personProvider) {

        this.abstractBankRepository = abstractBankRepository;
        this.abstractBankConverter = abstractBankConverter;
        this.personProvider = personProvider;
    }

    @Transactional
    public DTO create(NoIdDTO dto){
        return update(dto, false);
    }

    @Transactional
    public DTO update(DTO dto){
        return update(dto, true);
    }

    private DTO update(NoIdDTO dto, boolean isMustId){
        if(dto == null){
            return null;
        }

        Entity entity = abstractBankConverter.convertToEntity(dto);
        if(isMustId && entity.getId() == null){
            throw new MustIdException("Card id should be given");
        }

        assertExistById(entity);
        assertUniqueByIdentify(entity);

        entity.setPerson(personProvider.getCurrentPerson());

        entity = abstractBankRepository.saveAndFlush(entity);
        return abstractBankConverter.convertToDTO(entity);
    }

    private void assertExistById(Entity entity){
        if(entity.getId() == null){
            return;
        }

        abstractBankRepository.findById(entity.getId()).orElseThrow(
                () -> new NotFoundException(entity.getClass().getSimpleName()
                        + " with id " + entity.getId() + " not found")
        );
    }

    private void assertUniqueByIdentify(Entity entity){
        Entity founded = abstractBankRepository.findByIdentify(entity.getIdentify());
        if(founded != null && !founded.getId().equals(entity.getId())){
            throw new NotUniqueException(entity.getClass().getSimpleName()
                    + " with identify: " + founded.getIdentify() + " already exist");
        }
    }

    public DTO getById(Long id) {
        Entity entity = abstractBankRepository.findById(id).orElse(null);
        return abstractBankConverter.convertToDTO(entity);
    }

    public Set<DTO> getAll() {
        return abstractBankRepository.findAllByPerson(personProvider.getCurrentPerson()).stream()
                .map(abstractBankConverter::convertToDTO)
                .collect(Collectors.toSet());
    }

    public Set<Long> getAllBankIdByPersonId(Long personId) {
        return abstractBankRepository.findAllBankIdByPersonId(personId);
    }

    @Transactional
    public void deleteById(Long id) {
        abstractBankRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllByPersonId(Long personId) {
        abstractBankRepository.deleteByPersonId(personId);
    }
}
