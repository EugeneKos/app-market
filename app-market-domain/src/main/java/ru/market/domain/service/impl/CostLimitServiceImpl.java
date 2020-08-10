package ru.market.domain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.CostLimitConverter;
import ru.market.domain.converter.DateTimeConverter;
import ru.market.domain.data.Cost;
import ru.market.domain.data.CostLimit;
import ru.market.domain.exception.BadRequestException;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.repository.CostLimitRepository;
import ru.market.domain.service.ICostLimitService;
import ru.market.domain.service.IOperationService;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(CostLimitServiceImpl.class);

    private CostLimitRepository costLimitRepository;
    private CostLimitConverter costLimitConverter;

    private CommonValidator<CostLimit> validator;

    private IOperationService operationService;

    private IPersonProvider personProvider;

    public CostLimitServiceImpl(CostLimitRepository costLimitRepository,
                                CostLimitConverter costLimitConverter,
                                IOperationService operationService,
                                CommonValidator<CostLimit> validator,
                                IPersonProvider personProvider) {

        this.costLimitRepository = costLimitRepository;
        this.costLimitConverter = costLimitConverter;
        this.operationService = operationService;
        this.validator = validator;
        this.personProvider = personProvider;
    }

    @Transactional
    @Override
    public CostLimitDTO create(CostLimitNoIdDTO costLimitNoIdDTO) {
        LOGGER.info("Создание лимита на затраты");
        if(costLimitNoIdDTO == null){
            throw new BadRequestException("Данные лимита на затраты не заданы");
        }

        CostLimit costLimit = costLimitConverter.convertToEntity(costLimitNoIdDTO);

        validator.validate(costLimit);

        costLimit.setPerson(personProvider.getCurrentPerson());

        costLimit = costLimitRepository.saveAndFlush(costLimit);
        return costLimitConverter.convertToDTO(costLimit);
    }

    @Override
    public CostLimit getCostLimitById(Long id) {
        LOGGER.info("Получение лимита на затраты по id: {}", id);
        return costLimitRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Cost limit with id %d not found", id))
        );
    }

    @Override
    public CostLimitInfoDTO getCostLimitInfoById(Long id, String dateStr) {
        LOGGER.info("Получение детальной информации лимита на затраты по id: {} и дате: {}", id, dateStr);
        LocalDate date = DateTimeConverter.convertToLocalDate(dateStr);

        CostLimit costLimit = getCostLimitById(id);
        LOGGER.debug("Дата начала лимита = {} дата окончания лимита = {} . " +
                "Id лимита = {}", costLimit.getBeginDate(), costLimit.getEndDate(), id);

        if(date.isBefore(costLimit.getBeginDate()) || date.isAfter(costLimit.getEndDate())){
            throw new IllegalArgumentException("Запрашиваемая дата находится вне диапозона запрашиваемого лимита");
        }

        BigDecimal sumAllCosts = costLimitRepository.sumAllCosts(id).orElse(new BigDecimal(0));
        LOGGER.debug("Сумма всех затрат по лимиту = {} . Id лимита = {}", sumAllCosts, id);
        BigDecimal spendingPerDay = costLimitRepository.sumAllCostsByDate(id, date).orElse(new BigDecimal(0));
        LOGGER.debug("Сумма затрат по лимиту = {} по дате: {} . Id лимита = {}", spendingPerDay, date, id);

        CostLimitInfoDTO costLimitInfoDTO = costLimitConverter.convertToCostLimitInfoDTO(costLimit);

        costLimitInfoDTO.setRemain(costLimit.getSum().subtract(sumAllCosts).toString());
        costLimitInfoDTO.setSpendingPerDay(spendingPerDay.toString());
        costLimitInfoDTO.setAvailableToday(calculateAvailableToday(costLimit, date, spendingPerDay));

        return costLimitInfoDTO;
    }

    private String calculateAvailableToday(CostLimit costLimit, LocalDate date, BigDecimal spendingPerDay){
        int numRemainDays = getNumRemainDays(costLimit); // число оставшихся дней
        LOGGER.debug("Число оставшихся дней = {} . Дата запроса лимита: {} . Id лимита = {} ", numRemainDays, date, costLimit.getId());

        Long costLimitId = costLimit.getId();

        LocalDate beginDate = costLimit.getBeginDate();
        LocalDate yesterday = LocalDate.now().minusDays(1);

        BigDecimal limitSum = costLimit.getSum();
        BigDecimal pastExpenses; // затраты за прошедшие дни

        if(date.isBefore(LocalDate.now())){
            LOGGER.debug("Запрашиваемая дата лимита ранее текущей даты, вычитаем затраты за сегодняшний день. " +
                    "SpendingPerDay = {} . Дата запроса лимита: {} . Id лимита = {}", spendingPerDay, date, costLimitId);

            numRemainDays ++;

            LOGGER.debug("Увеличиваем число оставшихся дней на 1. NumRemainDays = {} . " +
                    "Дата запроса лимита: {} . Id лимита = {}", numRemainDays, date, costLimitId);

            pastExpenses = costLimitRepository.sumAllCostsByPeriod(costLimitId, beginDate, yesterday)
                    .orElse(new BigDecimal(0))
                    .subtract(spendingPerDay);

            LOGGER.debug("Затраты за прошедшие дни без учета сегодняшнего дня = {} . " +
                    "Дата запроса лимита: {} . Id лимита = {}", pastExpenses, date, costLimitId);
        } else {

            pastExpenses = costLimitRepository.sumAllCostsByPeriod(costLimitId, beginDate, yesterday)
                    .orElse(new BigDecimal(0));

            LOGGER.debug("Затраты за прошедшие дни = {} . Дата запроса лимита: {} . " +
                    "Id лимита = {}", pastExpenses, date, costLimitId);
        }

        String availableToday = limitSum.subtract(pastExpenses)
                .divide(new BigDecimal(numRemainDays), 2)
                .subtract(spendingPerDay)
                .toString();

        LOGGER.debug("Итоговая сумма, которая доступна по лимиту = {} . Дата запроса: {} . " +
                "Id лимита = {}", availableToday, date, costLimitId);

        return availableToday;
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
        LOGGER.info("Получение всех лимитов на затраты. PersonId = {}", personProvider.getCurrentPersonId());
        return costLimitRepository.findAllByPersonId(personProvider.getCurrentPersonId()).stream()
                .map(costLimitConverter::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Long> getAllIdByPersonId(Long personId) {
        LOGGER.info("Получение всех id лимитов на затраты по personId = {}", personId);
        return costLimitRepository.findAllIdByPersonId(personId);
    }

    @Transactional
    @Override
    public void deleteById(Long id, Boolean rollbackOperations) {
        LOGGER.info("Удаление лимита на затраты по id: {} . Признак отката операции: {}", id, rollbackOperations);
        if(rollbackOperations){
            rollbackOperations(costLimitRepository.findByIdWithCosts(id));
        }

        costLimitRepository.deleteById(id);
    }

    private void rollbackOperations(CostLimit costLimit){
        if(costLimit == null){
            return;
        }

        for (Cost cost : costLimit.getCosts()){
            operationService.rollback(cost.getOperation());
        }
    }
}
