package ru.market.domain.service.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.AbstractDefaultConverter;
import ru.market.domain.data.BankAccount;
import ru.market.domain.event.AccountDeleteEvent;
import ru.market.domain.exception.MustIdException;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.repository.account.AccountRepository;
import ru.market.domain.service.IPersonProvider;
import ru.market.domain.validator.CommonValidator;
import ru.market.utils.AccountLockHolder;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractAccountService<Entity extends BankAccount, NoIdDTO, DTO extends NoIdDTO> {
    private AccountRepository<Entity> accountRepository;
    private AbstractDefaultConverter<Entity, NoIdDTO, DTO> abstractDefaultConverter;

    private CommonValidator<Entity> validator;

    private IPersonProvider personProvider;

    private ApplicationEventPublisher eventPublisher;

    AbstractAccountService(AccountRepository<Entity> accountRepository,
                           AbstractDefaultConverter<Entity, NoIdDTO, DTO> abstractDefaultConverter,
                           CommonValidator<Entity> validator,
                           IPersonProvider personProvider,
                           ApplicationEventPublisher eventPublisher) {

        this.accountRepository = accountRepository;
        this.abstractDefaultConverter = abstractDefaultConverter;
        this.validator = validator;
        this.personProvider = personProvider;
        this.eventPublisher = eventPublisher;
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
            throw new MustIdException("Account id should be given");
        }

        assertExistById(entity);

        validator.validate(entity);

        entity.setPerson(personProvider.getCurrentPerson());

        entity = accountRepository.saveAndFlush(entity);
        return abstractDefaultConverter.convertToDTO(entity);
    }

    private void assertExistById(Entity entity){
        if(entity.getId() == null){
            return;
        }

        getAccount(entity.getId());
    }

    public void save(Entity entity){
        accountRepository.saveAndFlush(entity);
    }

    public Entity getAccount(Long id){
        return accountRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Account with id %d not found", id))
        );
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
        eventPublisher.publishEvent(new AccountDeleteEvent(this, id));
        accountRepository.deleteById(id);
        AccountLockHolder.removeAccountLock(id);
    }
}
