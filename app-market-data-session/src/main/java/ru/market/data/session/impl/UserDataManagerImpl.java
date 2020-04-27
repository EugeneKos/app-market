package ru.market.data.session.impl;

import ru.market.data.session.api.UserDataManager;
import ru.market.data.session.data.UserData;

public class UserDataManagerImpl implements UserDataManager {
    @Override
    public void setUserData(UserData userData) {
        SessionContext.setSessionAttribute(SessionAttributeNames.USER_DATA, userData);
    }

    @Override
    public UserData getUserData() {
        return SessionContext.getSessionAttribute(SessionAttributeNames.USER_DATA);
    }

    @Override
    public void removeUserData() {
        SessionContext.removeSessionAttribute(SessionAttributeNames.USER_DATA);
    }
}
