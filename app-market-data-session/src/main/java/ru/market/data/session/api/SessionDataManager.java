package ru.market.data.session.api;

public interface SessionDataManager {
    void setSecretKey(String secretKey);
    String getSecretKey();
}
