package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.AbstractDefaultConverter;
import ru.market.domain.data.BankAccount;
import ru.market.domain.exception.MustIdException;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.repository.AbstractAccountRepository;
import ru.market.domain.service.IPersonProvider;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractAccountService<Entity extends BankAccount, NoIdDTO, DTO extends NoIdDTO> {
    private AbstractAccountRepository<Entity> abstractAccountRepository;
    private AbstractDefaultConverter<Entity, NoIdDTO, DTO> abstractDefaultConverter;

    private IPersonProvider personProvider;

    AbstractAccountService(AbstractAccountRepository<Entity> abstractAccountRepository,
                           AbstractDefaultConverter<Entity, NoIdDTO, DTO> abstractDefaultConverter,
                           IPersonProvider personProvider) {

        this.abstractAccountRepository = abstractAccountRepository;
        this.abstractDefaultConverter = abstractDefaultConverter;
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

        Entity entity = abstractDefaultConverter.convertToEntity(dto);
        if(isMustId && entity.getId() == null){
            throw new MustIdException("Card id should be given");
        }

        assertExistById(entity);
        // todo: Не забыть про валидацию
        assertUniqueByIdentify(entity);

        entity.setPerson(personProvider.getCurrentPerson());

        entity = abstractAccountRepository.saveAndFlush(entity);
        return abstractDefaultConverter.convertToDTO(entity);
    }

    private void assertExistById(Entity entity){
        if(entity.getId() == null){
            return;
        }

        abstractAccountRepository.findById(entity.getId()).orElseThrow(
                () -> new NotFoundException(entity.getClass().getSimpleName()
                        + " with id " + entity.getId() + " not found")
        );
    }

    private void assertUniqueByIdentify(Entity entity){
        /*Entity founded = abstractAccountRepository.findByIdentify(entity.getIdentify());
        if(founded != null && !founded.getId().equals(entity.getId())){
            throw new NotUniqueException(entity.getClass().getSimpleName()
                    + " with identify: " + founded.getIdentify() + " already exist");
        }*/
    }

    public DTO getById(Long id) {
        Entity entity = abstractAccountRepository.findById(id).orElse(null);
        return abstractDefaultConverter.convertToDTO(entity);
    }

    public Set<DTO> getAll() {
        return abstractAccountRepository.findAllByPerson(personProvider.getCurrentPerson()).stream()
                .map(abstractDefaultConverter::convertToDTO)
                .collect(Collectors.toSet());
    }

    public Set<Long> getAllAccountIdByPersonId(Long personId) {
        return abstractAccountRepository.findAllAccountIdByPersonId(personId);
    }

    @Transactional
    public void deleteById(Long id) {
        abstractAccountRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllByPersonId(Long personId) {
        abstractAccountRepository.deleteByPersonId(personId);
    }
}
