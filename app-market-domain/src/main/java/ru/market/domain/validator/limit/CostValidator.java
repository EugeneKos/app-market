package ru.market.domain.validator.limit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.domain.data.Cost;
import ru.market.domain.data.CostLimit;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.validator.CommonValidator;
import ru.market.domain.validator.utils.ValidatorUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CostValidator implements CommonValidator<Cost> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CostValidator.class);

    private CostLimit costLimit;

    public CostValidator(CostLimit costLimit) {
        this.costLimit = costLimit;
    }

    @Override
    public void validate(Cost cost) throws ValidateException {
        validateSum(cost);
        validateDate(cost);
    }

    private void validateSum(Cost cost){
        LOGGER.info("Валидация суммы затраты");
        BigDecimal sum = cost.getSum();
        LOGGER.debug("Cost [validate sum] sum = {}", sum);

        if(sum == null){
            throw new ValidateException("Сумма затраты должна быть заполнена");
        }

        if(ValidatorUtils.isMatchMoney(sum.toString())){
            LOGGER.info("Валидация суммы затраты прошла успешно");
            return;
        }

        throw new ValidateException("Сумма затраты заполнена некорректно");
    }

    private void validateDate(Cost cost){
        LOGGER.info("Валидация даты затраты");
        LocalDate date = cost.getDate();

        if(costLimit == null){
            throw new ValidateException("Ошибка валидации затраты, лимит на данную затрату не установлен");
        }

        LocalDate beginDate = costLimit.getBeginDate();
        LocalDate endDate = costLimit.getEndDate();

        LOGGER.debug("Cost [validate date] costDate = {}, costLimitBeginDate = {}, costLimitEndDate ={}", date, beginDate, endDate);

        if(date.isBefore(beginDate)){
            throw new ValidateException("Дата затраты не должна быть ранее даты начала лимита");
        }
        if(date.isAfter(endDate)){
            throw new ValidateException("Дата затраты не должна быть позднее даты окончания лимита");
        }
        LOGGER.info("Валидация даты затраты прошла успешно");
    }
}
