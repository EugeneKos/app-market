package ru.market.utils;

import java.util.HashMap;
import java.util.Map;

public final class AccountLockHolder {
    private static final Map<Long, Object> accountLockMap = new HashMap<>();

    private AccountLockHolder(){}

    public static Object getAccountLockById(Long accountId){
        Object lock = accountLockMap.get(accountId);
        if(lock != null){
            return lock;
        }

        synchronized (AccountLockHolder.class){
            lock = accountLockMap.get(accountId);
            if(lock != null){
                return lock;
            }

            lock = new Object();
            accountLockMap.put(accountId, lock);
        }

        return lock;
    }

    public static void removeAccountLock(Long accountId){
        accountLockMap.remove(accountId);
    }
}
