package ru.market.domain.validator.limit;

import ru.market.domain.data.Cost;
import ru.market.domain.data.CostLimit;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.validator.CommonValidator;
import ru.market.domain.validator.utils.ValidatorUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CostValidator implements CommonValidator<Cost> {
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
        BigDecimal sum = cost.getSum();

        if(sum == null){
            throw new ValidateException("Сумма затраты должна быть заполнена");
        }

        if(ValidatorUtils.isMatchMoney(sum.toString())){
            return;
        }

        throw new ValidateException("Сумма затраты заполнена некорректно");
    }

    private void validateDate(Cost cost){
        LocalDate date = cost.getDate();

        if(costLimit == null){
            throw new ValidateException("Ошибка валидации затраты, лимит на данную затрату не установлен");
        }

        LocalDate beginDate = costLimit.getBeginDate();
        LocalDate endDate = costLimit.getEndDate();

        if(date.isBefore(beginDate)){
            throw new ValidateException("Дата затраты не должна быть ранее даты начала лимита");
        }
        if(date.isAfter(endDate)){
            throw new ValidateException("Дата затраты не должна быть позднее даты окончания лимита");
        }
    }
}
