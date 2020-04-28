package ru.market.data.session.api;

public interface SessionDataManager {
    void setUserData(UserData userData);
    UserData getUserData();

    <T> void setCurrentRequestBody(T body);
    <T> T getCurrentRequestBody();

    void setSecretKey(String secretKey);
    String getSecretKey();
}
