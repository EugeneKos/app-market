package ru.market.utils.cache.impl;

import ru.market.utils.cache.Cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CacheImpl<T> implements Cache<T> {
    private final int cacheSize;
    private final int timeToLiveSeconds;
    
    private Map<String, T> cacheData = new HashMap<>();
    private Map<String, Long> valueAddedTimeMap = new HashMap<>();

    public CacheImpl(int cacheSize, int timeToLiveSeconds) {
        this.cacheSize = cacheSize;
        this.timeToLiveSeconds = timeToLiveSeconds;
    }

    @Override
    public void put(String key, T value) {
        if(cacheData.size() == cacheSize){
            clearCache();
        }
        
        valueAddedTimeMap.put(key, currentTimeSeconds());
        cacheData.put(key, value);
    }

    @Override
    public T get(String key) {
        T value = cacheData.get(key);
        if(value == null){
            return null;
        }

        Long valueAddedTime = valueAddedTimeMap.get(key);
        if(currentTimeSeconds() - valueAddedTime >= timeToLiveSeconds){
            cacheData.remove(key);
            valueAddedTimeMap.remove(key);
            return null;
        }

        return value;
    }
    
    private long currentTimeSeconds(){
        return System.currentTimeMillis() / 1000;
    }

    private void clearCache(){
        long minTime = Long.MAX_VALUE;
        long currentTime = currentTimeSeconds();
        int numberOfDeleted = 0;
        String key = null;

        Iterator<Map.Entry<String, Long>> entryIterator = valueAddedTimeMap.entrySet().iterator();
        while (entryIterator.hasNext()){
            Map.Entry<String, Long> next = entryIterator.next();
            Long currentAddedTime = next.getValue();

            if(currentTime - currentAddedTime >= timeToLiveSeconds){
                cacheData.remove(next.getKey());
                entryIterator.remove();
                numberOfDeleted ++;
            } else if(currentAddedTime < minTime){
                minTime = currentAddedTime;
                key = next.getKey();
            }
        }

        if(numberOfDeleted == 0){
            cacheData.remove(key);
            valueAddedTimeMap.remove(key);
        }
    }
}
