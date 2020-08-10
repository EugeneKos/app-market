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
public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String middleName;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "RegistrationDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + ((password == null || password.isEmpty()) ? "[]" : "[********]") + '\'' +
                '}';
    }
}
