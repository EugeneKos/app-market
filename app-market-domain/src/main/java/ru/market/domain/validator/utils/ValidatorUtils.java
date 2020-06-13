package ru.market.domain.validator.utils;

public final class ValidatorUtils {
    public static boolean isMatchMoney(String moneyAmount){
        if(moneyAmount.matches("[0-9]+")){
            return true;
        }
        return moneyAmount.matches("\\d+[.]\\d{1,2}");
    }
}
