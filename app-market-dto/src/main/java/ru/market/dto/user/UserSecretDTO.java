package ru.market.dto.user;

public class UserSecretDTO extends UserDTO {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
