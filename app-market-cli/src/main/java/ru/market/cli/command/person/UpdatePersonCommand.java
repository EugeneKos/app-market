package ru.market.cli.command.person;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.Printer;
import ru.market.client.rest.PersonRestClient;
import ru.market.dto.person.PersonDTO;

import java.util.Map;

import static ru.market.cli.command.CommandArguments.FIRST_NAME_ARG;
import static ru.market.cli.command.CommandArguments.ID_ARG;
import static ru.market.cli.command.CommandArguments.LAST_NAME_ARG;
import static ru.market.cli.command.CommandArguments.MIDDLE_NAME_ARG;

public class UpdatePersonCommand implements Command {
    private PersonRestClient personRestClient;
    private Printer printer;

    public UpdatePersonCommand(PersonRestClient personRestClient, Printer printer) {
        this.personRestClient = personRestClient;
        this.printer = printer;
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
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(Long.parseLong(arguments.get(ID_ARG)));
        personDTO.setFirstName(arguments.get(FIRST_NAME_ARG));
        personDTO.setLastName(arguments.get(LAST_NAME_ARG));
        personDTO.setMiddleName(arguments.get(MIDDLE_NAME_ARG));

        PersonDTO updated = personRestClient.update(personDTO);
        printer.print(updated);
    }
}
