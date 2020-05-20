package ru.market.domain.data.enumeration;

public enum OperationType {
    ENROLLMENT, DEBIT;

    public static OperationType getByName(String name){
        for (OperationType type : OperationType.values()){
            if(type.toString().equalsIgnoreCase(name)){
                return type;
            }
        }

        return null;
    }
}
