package ru.market.domain.converter;

import ru.market.domain.data.BankAccount;

public abstract class AbstractBankConverter<Entity extends BankAccount, NoIdDTO, DTO extends NoIdDTO> {
    public abstract DTO convertToDTO(Entity entity);
    public abstract Entity convertToEntity(NoIdDTO dto);
}
