package ru.market.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.ToString;
import ru.market.dto.person.PersonDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "userBuilder")
@ToString(callSuper = true)
public class UserDTO extends UserUsernameDTO {
    private Long id;

    private PersonDTO person;
}
