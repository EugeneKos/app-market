package ru.market.cli.command.person;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.Printer;
import ru.market.client.rest.PersonRestClient;
import ru.market.dto.person.PersonDTO;

import java.util.Map;

public class UpdatePersonCommand implements Command {
    private static final Argument ID_ARG = new Argument("-i", "--id", true);
    private static final Argument FIRST_NAME_ARG = new Argument("-f", "--firstName", true);
    private static final Argument LAST_NAME_ARG = new Argument("-l", "--lastName", true);
    private static final Argument MIDDLE_NAME_ARG = new Argument("-m", "--middleName", true);

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
