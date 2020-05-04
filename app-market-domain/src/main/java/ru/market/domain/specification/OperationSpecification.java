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

    public static Specification<Operation> createSpecification(Long accountId, OperationFilterDTO filter){
        Specification<Operation> specification = specificationByAccountId(accountId);

        if(StringUtils.isNoneEmpty(filter.getDateStr())){
            specification = specification.and(specificationByDate(filter.getDateStr()));
        }
        if(StringUtils.isNoneEmpty(filter.getAmount())){
            specification = specification.and(specificationByAmount(filter.getAmount()));
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

    private static Specification<Operation> specificationByAccountId(Long accountId){
        return (Specification<Operation>) (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(SpecificationFields.BANK_ACCOUNT).get(SpecificationFields.ID), accountId);
    }

    private static Specification<Operation> specificationByDate(String dateStr){
        LocalDate date = DateTimeConverter.convertToLocalDate(dateStr);

        return (Specification<Operation>) (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(SpecificationFields.DATE), date);
    }

    private static Specification<Operation> specificationByAmount(String amount){
        return (Specification<Operation>) (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(SpecificationFields.AMOUNT), amount);
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