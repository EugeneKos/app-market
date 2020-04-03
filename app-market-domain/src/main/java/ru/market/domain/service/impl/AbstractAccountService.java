package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.AbstractDefaultConverter;
import ru.market.domain.data.BankAccount;
import ru.market.domain.exception.MustIdException;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.repository.account.AccountRepository;
import ru.market.domain.service.IPersonProvider;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractAccountService<Entity extends BankAccount, NoIdDTO, DTO extends NoIdDTO> {
    private AccountRepository<Entity> accountRepository;
    private AbstractDefaultConverter<Entity, NoIdDTO, DTO> abstractDefaultConverter;

    private IPersonProvider personProvider;

    AbstractAccountService(AccountRepository<Entity> accountRepository,
                           AbstractDefaultConverter<Entity, NoIdDTO, DTO> abstractDefaultConverter,
                           IPersonProvider personProvider) {

        this.accountRepository = accountRepository;
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

        entity = accountRepository.saveAndFlush(entity);
        return abstractDefaultConverter.convertToDTO(entity);
    }

    private void assertExistById(Entity entity){
        if(entity.getId() == null){
            return;
        }

        accountRepository.findById(entity.getId()).orElseThrow(
                () -> new NotFoundException(entity.getClass().getSimpleName()
                        + " with id " + entity.getId() + " not found")
        );
    }

    private void assertUniqueByIdentify(Entity entity){
        /*Entity founded = accountRepository.findByIdentify(entity.getIdentify());
        if(founded != null && !founded.getId().equals(entity.getId())){
            throw new NotUniqueException(entity.getClass().getSimpleName()
                    + " with identify: " + founded.getIdentify() + " already exist");
        }*/
    }

    public DTO getById(Long id) {
        Entity entity = accountRepository.findById(id).orElse(null);
        return abstractDefaultConverter.convertToDTO(entity);
    }

    public Set<DTO> getAll() {
        return accountRepository.findAllByPerson(personProvider.getCurrentPerson()).stream()
                .map(abstractDefaultConverter::convertToDTO)
                .collect(Collectors.toSet());
    }

    public Set<Long> getAllAccountIdByPersonId(Long personId) {
        return accountRepository.findAllAccountIdByPersonId(personId);
    }

    @Transactional
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllByPersonId(Long personId) {
        accountRepository.deleteByPersonId(personId);
    }
}
