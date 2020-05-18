package ru.market.utils.cache;

public interface Cache<T> {
    void put(String key, T value);
    T get(String key);
}
