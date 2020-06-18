package ru.market.domain.validator.limit;

import ru.market.domain.data.CostLimit;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.repository.CostLimitRepository;
import ru.market.domain.validator.CommonValidator;
import ru.market.domain.validator.utils.ValidatorUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CostLimitValidator implements CommonValidator<CostLimit> {
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
        BigDecimal sum = costLimit.getSum();

        if(sum == null){
            throw new ValidateException("Сумма лимита должна быть заполнена");
        }

        if(ValidatorUtils.isMatchMoney(sum.toString())){
            return;
        }

        throw new ValidateException("Сумма лимита заполнена некорректно");
    }

    private void validateDates(CostLimit costLimit){
        LocalDate beginDate = costLimit.getBeginDate();
        LocalDate endDate = costLimit.getEndDate();

        if(beginDate.isAfter(endDate)){
            throw new ValidateException("Дата начала лимита не может быть позднее даты завершения лимита");
        }
    }

    private void validateSumAndPeriod(CostLimit costLimit) throws ValidateException {
        try {
            assertUniqueBySumAndPeriod(costLimit);
        } catch (Exception e){
            throw new ValidateException("Ошибка валидации лимита", e);
        }
    }

    private void assertUniqueBySumAndPeriod(CostLimit costLimit){
        BigDecimal sum = costLimit.getSum();

        LocalDate beginDate = costLimit.getBeginDate();
        LocalDate endDate = costLimit.getEndDate();

        CostLimit founded = costLimitRepository.findBySumAndPeriod(sum, beginDate, endDate);

        if(founded != null){
            throw new NotUniqueException(String.format(
                    "Cost limit with sum = %s, begin date: %s, and date: %s already exist", sum, beginDate, endDate)
            );
        }
    }
}
