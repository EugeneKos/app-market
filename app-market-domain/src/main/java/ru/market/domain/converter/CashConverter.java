package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import ru.market.domain.data.Cash;
import ru.market.dto.cash.CashDTO;
import ru.market.dto.cash.CashNoIdDTO;

@Service
public class CashConverter {
    private DozerBeanMapper mapper;

    public CashConverter(DozerBeanMapper mapper) {
        this.mapper = mapper;
    }

    public Cash convertToCash(CashNoIdDTO cashDTO){
        if(cashDTO == null){
            return null;
        }
        return mapper.map(cashDTO, Cash.class);
    }

    public CashDTO convertToCashDTO(Cash cash){
        if(cash == null){
            return null;
        }
        return mapper.map(cash, CashDTO.class);
    }
}
