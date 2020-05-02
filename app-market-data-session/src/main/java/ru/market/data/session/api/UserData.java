package ru.market.data.session.api;

public class UserData {
    private Long userId;
    private Long personId;
    private boolean authenticate;
    private String secretKey;
    private int passwordAttemptCount;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public boolean isAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(boolean authenticate) {
        this.authenticate = authenticate;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getPasswordAttemptCount() {
        return passwordAttemptCount;
    }

    public void setPasswordAttemptCount(int passwordAttemptCount) {
        this.passwordAttemptCount = passwordAttemptCount;
    }
}
