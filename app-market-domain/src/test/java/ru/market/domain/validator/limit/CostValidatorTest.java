package ru.market.domain.validator.limit;

import org.junit.Test;

import ru.market.domain.data.Cost;
import ru.market.domain.data.CostLimit;
import ru.market.domain.exception.ValidateException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CostValidatorTest {
    @Test
    public void validateTest(){
        CostValidator costValidator = createValidator();

        Cost cost = new Cost();
        cost.setSum(new BigDecimal("250"));
        cost.setDate(LocalDate.of(2020, 6, 15));

        costValidator.validate(cost);
    }

    @Test(expected = ValidateException.class)
    public void costDateBeforeCostLimitPeriodTest(){
        CostValidator costValidator = createValidator();

        Cost cost = new Cost();
        cost.setSum(new BigDecimal("250"));
        cost.setDate(LocalDate.of(2020, 5, 15));

        costValidator.validate(cost);
    }

    @Test(expected = ValidateException.class)
    public void costDateAfterCostLimitPeriodTest(){
        CostValidator costValidator = createValidator();

        Cost cost = new Cost();
        cost.setSum(new BigDecimal("250"));
        cost.setDate(LocalDate.of(2020, 6, 28));

        costValidator.validate(cost);
    }

    @Test(expected = ValidateException.class)
    public void incorrectSumTest(){
        CostValidator costValidator = createValidator();

        Cost cost = new Cost();
        cost.setSum(new BigDecimal("250.589"));
        cost.setDate(LocalDate.of(2020, 6, 15));

        costValidator.validate(cost);
    }

    private CostValidator createValidator(){
        CostLimit costLimit = new CostLimit();
        costLimit.setBeginDate(LocalDate.of(2020, 5, 25));
        costLimit.setEndDate(LocalDate.of(2020, 6, 25));

        return new CostValidator(costLimit);
    }
}