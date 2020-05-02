package ru.market.dto.user;

import java.time.LocalDateTime;

public class UserAdditionalDTO extends UserDTO {
    private String password;
    private UserStatus status;
    private LocalDateTime timestampStatus;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestampStatus() {
        return timestampStatus;
    }

    public void setTimestampStatus(LocalDateTime timestampStatus) {
        this.timestampStatus = timestampStatus;
    }
}