package ru.market.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "userAdditionalBuilder")
public class UserAdditionalDTO extends UserDTO {
    private String password;
    private UserStatus status;
    private LocalDateTime timestampStatus;
    private Integer passwordAttemptCount;
}
