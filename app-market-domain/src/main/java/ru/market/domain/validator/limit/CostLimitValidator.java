package ru.market.domain.validator.limit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.domain.data.CostLimit;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.repository.CostLimitRepository;
import ru.market.domain.validator.CommonValidator;
import ru.market.domain.validator.utils.ValidatorUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CostLimitValidator implements CommonValidator<CostLimit> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CostLimitValidator.class);

    private CostLimitRepository costLimitRepository;

    public CostLimitValidator(CostLimitRepository costLimitRepository) {
        this.costLimitRepository = costLimitRepository;
    }

    @Override
    public void validate(CostLimit costLimit) throws ValidateException {
        validateSum(costLimit);
        validateDates(costLimit);
        validateSumAndPeriod(costLimit);
    }

    private void validateSum(CostLimit costLimit){
        LOGGER.info("Валидация суммы лимита на затраты");
        BigDecimal sum = costLimit.getSum();
        LOGGER.debug("CostLimit [validate sum] sum = {}", sum);

        if(sum == null){
            throw new ValidateException("Сумма лимита должна быть заполнена");
        }

        if(ValidatorUtils.isMatchMoney(sum.toString())){
            LOGGER.info("Валидация суммы лимита на затраты прошла успешно");
            return;
        }

        throw new ValidateException("Сумма лимита заполнена некорректно");
    }

    private void validateDates(CostLimit costLimit){
        LOGGER.info("Валидация даты начала и даты окончания лимита на затраты");
        LocalDate beginDate = costLimit.getBeginDate();
        LocalDate endDate = costLimit.getEndDate();
        LOGGER.debug("CostLimit [validate dates] beginDate = {}, endDate = {}", beginDate, endDate);

        if(beginDate.isAfter(endDate)){
            throw new ValidateException("Дата начала лимита не может быть позднее даты завершения лимита");
        }
        LOGGER.info("Валидация даты начала и даты окончания лимита на затраты прошла успешно");
    }

    private void validateSumAndPeriod(CostLimit costLimit) throws ValidateException {
        try {
            assertUniqueBySumAndPeriod(costLimit);
        } catch (Exception e){
            throw new ValidateException("Ошибка валидации лимита", e);
        }
    }

    private void assertUniqueBySumAndPeriod(CostLimit costLimit){
        LOGGER.info("Валидация на уникальные параметры лимита на затраты");
        BigDecimal sum = costLimit.getSum();

        LocalDate beginDate = costLimit.getBeginDate();
        LocalDate endDate = costLimit.getEndDate();

        LOGGER.debug("CostLimit [unique sum and periods] sum = {}, beginDate = {}, endDate = {}", sum, beginDate, endDate);

        CostLimit founded = costLimitRepository.findBySumAndPeriod(sum, beginDate, endDate);

        if(founded != null){
            throw new NotUniqueException(String.format(
                    "Cost limit with sum = %s, begin date: %s, and date: %s already exist", sum, beginDate, endDate)
            );
        }
        LOGGER.info("Валидация на уникальные параметры лимита на затраты прошла успешно");
    }
}
