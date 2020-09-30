package ru.market.web.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.market.domain.exception.OperationExecuteException;
import ru.market.domain.exception.ValidateException;

import ru.market.dto.error.ErrorDTO;

@RestControllerAdvice
public class ExceptionHandlerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(ValidateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleValidateException(ValidateException e){
        LOGGER.error("Ошибка валидации запроса", e);
        return ErrorDTO.builder().error(e.getMessage()).build();
    }

    @ExceptionHandler(OperationExecuteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleOperationExecuteException(OperationExecuteException e){
        LOGGER.error("Ошибка выполнения операции над денежным счетом", e);
        return ErrorDTO.builder().error(e.getMessage()).build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(Exception e){
        LOGGER.error("Internal server error", e);
        return ErrorDTO.builder().error("Что-то пошло не так").build();
    }
}
