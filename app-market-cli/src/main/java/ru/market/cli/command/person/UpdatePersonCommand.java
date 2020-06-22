package ru.market.cli.command.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.utils.Printer;
import ru.market.client.rest.PersonRestClient;
import ru.market.dto.person.PersonDTO;

import java.util.Map;

import static ru.market.cli.command.CommandArguments.FIRST_NAME_ARG;
import static ru.market.cli.command.CommandArguments.ID_ARG;
import static ru.market.cli.command.CommandArguments.LAST_NAME_ARG;
import static ru.market.cli.command.CommandArguments.MIDDLE_NAME_ARG;

@Service
public class UpdatePersonCommand implements Command {
    private PersonRestClient personRestClient;

    @Autowired
    public UpdatePersonCommand(PersonRestClient personRestClient) {
        this.personRestClient = personRestClient;
    }

    @Override
    public String getName() {
        return "person-update";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{ID_ARG, FIRST_NAME_ARG, LAST_NAME_ARG, MIDDLE_NAME_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        PersonDTO personDTO = PersonDTO.personBuilder()
                .id(Long.parseLong(arguments.get(ID_ARG)))
                .build();

        personDTO.setFirstName(arguments.get(FIRST_NAME_ARG));
        personDTO.setLastName(arguments.get(LAST_NAME_ARG));
        personDTO.setMiddleName(arguments.get(MIDDLE_NAME_ARG));

        PersonDTO updated = personRestClient.update(personDTO);
        Printer.print(updated);
    }
}
