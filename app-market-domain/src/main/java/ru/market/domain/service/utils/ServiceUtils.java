package ru.market.domain.service.utils;

public final class ServiceUtils {
    public static boolean isMatchMoney(String moneyAmount){
        if(moneyAmount.matches("[0-9]+")){
            return true;
        }
        return moneyAmount.matches("\\d+[.]\\d{1,2}");
    }
}
