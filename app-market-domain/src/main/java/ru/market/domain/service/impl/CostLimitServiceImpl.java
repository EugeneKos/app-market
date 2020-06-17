package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.CostLimitConverter;
import ru.market.domain.converter.DateTimeConverter;
import ru.market.domain.data.CostLimit;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.repository.CostLimitRepository;
import ru.market.domain.service.ICostLimitService;
import ru.market.domain.service.IPersonProvider;
import ru.market.domain.validator.CommonValidator;

import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitInfoDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class CostLimitServiceImpl implements ICostLimitService {
    private CostLimitRepository costLimitRepository;
    private CostLimitConverter costLimitConverter;

    private CommonValidator<CostLimit> validator;

    private IPersonProvider personProvider;

    public CostLimitServiceImpl(CostLimitRepository costLimitRepository,
                                CostLimitConverter costLimitConverter,
                                CommonValidator<CostLimit> validator,
                                IPersonProvider personProvider) {

        this.costLimitRepository = costLimitRepository;
        this.costLimitConverter = costLimitConverter;
        this.validator = validator;
        this.personProvider = personProvider;
    }

    @Transactional
    @Override
    public CostLimitDTO create(CostLimitNoIdDTO costLimitNoIdDTO) {
        if(costLimitNoIdDTO == null){
            return null;
        }

        CostLimit costLimit = costLimitConverter.convertToEntity(costLimitNoIdDTO);

        validator.validate(costLimit);

        costLimit.setPerson(personProvider.getCurrentPerson());

        costLimit = costLimitRepository.saveAndFlush(costLimit);
        return costLimitConverter.convertToDTO(costLimit);
    }

    @Override
    public CostLimit getCostLimitById(Long id) {
        return costLimitRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Cost limit with id %d not found", id))
        );
    }

    @Override
    public CostLimitInfoDTO getCostLimitInfoById(Long id, String dateStr) {
        LocalDate date = DateTimeConverter.convertToLocalDate(dateStr);

        CostLimit costLimit = getCostLimitById(id);

        if(date.isBefore(costLimit.getBeginDate()) || date.isAfter(costLimit.getEndDate())){
            throw new IllegalArgumentException("Запрашиваемая дата находится вне диапозона запрашиваемого лимита");
        }

        BigDecimal sumAllCosts = costLimitRepository.sumAllCosts(id).orElse(new BigDecimal(0));
        BigDecimal spendingPerDay = costLimitRepository.sumAllCostsByDate(id, date).orElse(new BigDecimal(0));

        CostLimitInfoDTO costLimitInfoDTO = costLimitConverter.convertToCostLimitInfoDTO(costLimit);

        costLimitInfoDTO.setRemain(costLimit.getSum().subtract(sumAllCosts).toString());
        costLimitInfoDTO.setSpendingPerDay(spendingPerDay.toString());
        costLimitInfoDTO.setAvailableToday(calculateAvailableToday(costLimit, date, spendingPerDay));

        return costLimitInfoDTO;
    }

    private String calculateAvailableToday(CostLimit costLimit, LocalDate date, BigDecimal spendingPerDay){
        int numRemainDays = getNumRemainDays(costLimit); // число оставшихся дней

        Long costLimitId = costLimit.getId();

        LocalDate beginDate = costLimit.getBeginDate();
        LocalDate yesterday = LocalDate.now().minusDays(1);

        BigDecimal limitSum = costLimit.getSum();
        BigDecimal pastExpenses; // затраты за прошедшие дни

        if(date.isBefore(LocalDate.now())){
            numRemainDays ++;

            pastExpenses = costLimitRepository.sumAllCostsByPeriod(costLimitId, beginDate, yesterday)
                    .orElse(new BigDecimal(0))
                    .subtract(spendingPerDay);
        } else {

            pastExpenses = costLimitRepository.sumAllCostsByPeriod(costLimitId, beginDate, yesterday)
                    .orElse(new BigDecimal(0));
        }

        return limitSum.subtract(pastExpenses)
                .divide(new BigDecimal(numRemainDays), 2)
                .subtract(spendingPerDay)
                .toString();
    }

    private int getNumRemainDays(CostLimit costLimit){
        LocalDate endDate = costLimit.getEndDate();
        LocalDate beginDate = costLimit.getBeginDate();
        LocalDate now = LocalDate.now();

        int numRemainDays = 0;

        if(now.isAfter(endDate)){
            return numRemainDays;
        }

        LocalDate counterDate = LocalDate.from(endDate);

        while ((now.isBefore(counterDate) || now.isEqual(counterDate))
                && (counterDate.isAfter(beginDate) || counterDate.isEqual(beginDate))){

            numRemainDays ++;
            counterDate = counterDate.minusDays(1);
        }

        return numRemainDays;
    }

    @Override
    public Set<CostLimitDTO> getAll() {
        return costLimitRepository.findAllByPerson(personProvider.getCurrentPerson()).stream()
                .map(costLimitConverter::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Long> getAllIdByPersonId(Long personId) {
        return costLimitRepository.findAllIdByPersonId(personId);
    }

    @Override
    public void deleteById(Long id) {

    }
}
