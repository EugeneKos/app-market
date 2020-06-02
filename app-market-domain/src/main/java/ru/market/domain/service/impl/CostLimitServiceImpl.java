package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.CostLimitConverter;
import ru.market.domain.data.CostLimit;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.repository.CostLimitRepository;
import ru.market.domain.service.ICostLimitService;
import ru.market.domain.service.IPersonProvider;

import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

import java.util.Set;
import java.util.stream.Collectors;

public class CostLimitServiceImpl implements ICostLimitService {
    private CostLimitRepository costLimitRepository;
    private CostLimitConverter costLimitConverter;

    private IPersonProvider personProvider;

    public CostLimitServiceImpl(CostLimitRepository costLimitRepository,
                                CostLimitConverter costLimitConverter,
                                IPersonProvider personProvider) {

        this.costLimitRepository = costLimitRepository;
        this.costLimitConverter = costLimitConverter;
        this.personProvider = personProvider;
    }

    @Transactional
    @Override
    public CostLimitDTO create(CostLimitNoIdDTO costLimitNoIdDTO) {
        if(costLimitNoIdDTO == null){
            return null;
        }

        CostLimit costLimit = costLimitConverter.convertToEntity(costLimitNoIdDTO);
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
    public Set<CostLimitDTO> getAll() {
        return costLimitRepository.findAllByPerson(personProvider.getCurrentPerson()).stream()
                .map(costLimitConverter::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteById(Long id) {

    }
}
