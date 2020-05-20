package ru.market.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

final class CommonSpecification {
    private CommonSpecification(){}

    static <Entity> Specification<Entity> specificationByNumber(String databaseNumberField, String number){
        if(number.charAt(0) == '<'){
            if(number.charAt(1) == '='){
                return (Specification<Entity>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.le(root.get(databaseNumberField),
                                new BigDecimal(number.substring(2, number.length())));
            } else {
                return (Specification<Entity>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.lt(root.get(databaseNumberField),
                                new BigDecimal(number.substring(1, number.length())));
            }
        }
        if(number.charAt(0) == '>'){
            if(number.charAt(1) == '='){
                return (Specification<Entity>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.ge(root.get(databaseNumberField),
                                new BigDecimal(number.substring(2, number.length())));
            } else {
                return (Specification<Entity>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.gt(root.get(databaseNumberField),
                                new BigDecimal(number.substring(1, number.length())));
            }
        }

        return (Specification<Entity>) (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(databaseNumberField), new BigDecimal(number));
    }
}
