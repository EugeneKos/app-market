package ru.market.domain.specification;

import org.apache.commons.lang3.StringUtils;

import org.springframework.data.jpa.domain.Specification;

import ru.market.domain.converter.DateTimeConverter;
import ru.market.domain.data.Operation;
import ru.market.domain.data.enumeration.OperationType;
import ru.market.dto.operation.OperationFilterDTO;

import java.time.LocalDate;

public final class OperationSpecification {
    private OperationSpecification() {}

    public static Specification<Operation> createSpecification(Long moneyAccountId, OperationFilterDTO filter){
        Specification<Operation> specification = specificationByMoneyAccountId(moneyAccountId);

        if(StringUtils.isNoneEmpty(filter.getDateStr())){
            specification = specification.and(specificationByDate(filter.getDateStr()));
        }
        if(StringUtils.isNoneEmpty(filter.getAmount())){
            specification = specification.and(
                    CommonSpecification.specificationByNumber(SpecificationFields.AMOUNT, filter.getAmount())
            );
        }
        if(StringUtils.isNoneEmpty(filter.getOperationType())){
            OperationType operationType = OperationType.getByName(filter.getOperationType());
            if(operationType != null){
                specification = specification.and(specificationByOperationType(operationType));
            }
        }
        if(StringUtils.isNoneEmpty(filter.getDescription())){
            specification = specification.and(specificationByDescription(filter.getDescription()));
        }

        return specification;
    }

    private static Specification<Operation> specificationByMoneyAccountId(Long moneyAccountId){
        return (Specification<Operation>) (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(SpecificationFields.MONEY_ACCOUNT).get(SpecificationFields.ID), moneyAccountId);
    }

    private static Specification<Operation> specificationByDate(String dateStr){
        LocalDate date = DateTimeConverter.convertToLocalDate(dateStr);

        return (Specification<Operation>) (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(SpecificationFields.DATE), date);
    }

    private static Specification<Operation> specificationByOperationType(OperationType operationType){
        return (Specification<Operation>) (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(SpecificationFields.OPERATION_TYPE), operationType);
    }

    private static Specification<Operation> specificationByDescription(String description){
        return (Specification<Operation>) (root, query, criteriaBuilder) -> {
            String pattern = "%" + description + "%";
            return criteriaBuilder.like(root.get(SpecificationFields.DESCRIPTION), pattern);
        };
    }
}