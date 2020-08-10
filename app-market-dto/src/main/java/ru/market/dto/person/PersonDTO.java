package ru.market.dto.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "personBuilder")
@ToString(callSuper = true)
public class PersonDTO extends PersonNoIdDTO {
    private Long id;
}
