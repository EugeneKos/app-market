package ru.market.domain.validator;

import ru.market.domain.exception.ValidateException;

public interface CommonValidator<E> {
    void validate(E entity) throws ValidateException;
}
