package ru.market.data.session.api;

import ru.market.data.session.data.UserData;

public interface UserDataManager {
    void setUserData(UserData userData);
    UserData getUserData();
    void removeUserData();
}
