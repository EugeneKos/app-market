package ru.market.utils;

import java.util.HashMap;
import java.util.Map;

public final class MoneyAccountLockHolder {
    private static final Map<Long, Object> moneyAccountLockMap = new HashMap<>();

    private MoneyAccountLockHolder(){}

    public static Object getMoneyAccountLockById(Long moneyAccountId){
        Object lock = moneyAccountLockMap.get(moneyAccountId);
        if(lock != null){
            return lock;
        }

        synchronized (MoneyAccountLockHolder.class){
            lock = moneyAccountLockMap.get(moneyAccountId);
            if(lock != null){
                return lock;
            }

            lock = new Object();
            moneyAccountLockMap.put(moneyAccountId, lock);
        }

        return lock;
    }

    public static void removeMoneyAccountLock(Long moneyAccountId){
        moneyAccountLockMap.remove(moneyAccountId);
    }
}
