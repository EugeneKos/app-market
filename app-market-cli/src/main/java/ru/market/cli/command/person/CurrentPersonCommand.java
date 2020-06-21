package ru.market.cli.command.person;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.utils.Printer;
import ru.market.client.rest.PersonRestClient;
import ru.market.dto.person.PersonDTO;

import java.util.Map;

public class CurrentPersonCommand implements Command {
    private PersonRestClient personRestClient;

    public CurrentPersonCommand(PersonRestClient personRestClient) {
        this.personRestClient = personRestClient;
    }

    @Override
    public String getName() {
        return "person-get";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[0];
    }

    @Override
    public void perform(Map<Argument, String> map) {
        PersonDTO person = personRestClient.getCurrent();
        Printer.print(person);
    }
}
