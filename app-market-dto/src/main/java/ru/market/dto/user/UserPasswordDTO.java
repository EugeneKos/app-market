package ru.market.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPasswordDTO {
    private String oldPassword;
    private String newPassword;

    @Override
    public String toString() {
        return "UserPasswordDTO{" +
                "oldPassword='" + ((oldPassword == null || oldPassword.isEmpty()) ? "[]" : "[********]") + '\'' +
                ", newPassword='" + ((newPassword == null || newPassword.isEmpty()) ? "[]" : "[********]") + '\'' +
                '}';
    }
}
