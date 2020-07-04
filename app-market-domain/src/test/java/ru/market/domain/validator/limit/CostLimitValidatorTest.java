package ru.market.domain.validator.limit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.market.domain.config.DomainTestConfiguration;
import ru.market.domain.data.CostLimit;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.repository.CostLimitRepository;
import ru.market.domain.validator.CommonValidator;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainTestConfiguration.class)
@TestPropertySource(locations = "classpath:database-test-config.properties")
public class CostLimitValidatorTest {
    @Autowired
    private CommonValidator<CostLimit> costLimitValidator;

    @Autowired
    private CostLimitRepository costLimitRepository;

    @Test
    public void validateTest(){
        CostLimit costLimit_1 = createCostLimit("7500", "Test limit 1",
                LocalDate.of(2020, 5, 14), LocalDate.of(2020, 6, 14));

        costLimitRepository.saveAndFlush(costLimit_1);

        CostLimit costLimit = createCostLimit("7500", "Test limit 2",
                LocalDate.of(2020, 5, 18), LocalDate.of(2020, 6, 14));

        costLimitValidator.validate(costLimit);
    }

    @Test(expected = ValidateException.class)
    public void notUniqueSumAndPeriodTest(){
        CostLimit costLimit_1 = createCostLimit("8500", "Test limit 3",
                LocalDate.of(2020, 5, 14), LocalDate.of(2020, 6, 14));

        costLimitRepository.saveAndFlush(costLimit_1);

        CostLimit costLimit = createCostLimit("8500", "Test limit 3",
                LocalDate.of(2020, 5, 14), LocalDate.of(2020, 6, 14));

        costLimitValidator.validate(costLimit);
    }

    @Test(expected = ValidateException.class)
    public void incorrectSumTest(){
        CostLimit costLimit = new CostLimit();
        costLimit.setSum(new BigDecimal("7500.174"));

        costLimitValidator.validate(costLimit);
    }

    @Test(expected = ValidateException.class)
    public void incorrectDatesTest(){
        CostLimit costLimit = new CostLimit();
        costLimit.setSum(new BigDecimal("7500.17"));
        costLimit.setBeginDate(LocalDate.of(2020, 6, 25));
        costLimit.setEndDate(LocalDate.of(2020, 6, 12));

        costLimitValidator.validate(costLimit);
    }

    private CostLimit createCostLimit(String sum, String description, LocalDate beginDate, LocalDate endDate){
        CostLimit costLimit = new CostLimit();
        costLimit.setSum(new BigDecimal(sum));
        costLimit.setDescription(description);
        costLimit.setBeginDate(beginDate);
        costLimit.setEndDate(endDate);

        return costLimit;
    }
}