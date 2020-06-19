package ru.market.cli.command.person;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.Printer;
import ru.market.client.rest.PersonRestClient;
import ru.market.dto.person.PersonDTO;

import java.util.Map;

public class CurrentPersonCommand implements Command {
    private PersonRestClient personRestClient;
    private Printer printer;

    public CurrentPersonCommand(PersonRestClient personRestClient, Printer printer) {
        this.personRestClient = personRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "person";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[0];
    }

    @Override
    public void perform(Map<Argument, String> map) {
        try {
            PersonDTO person = personRestClient.getCurrent();
            printer.print(person);
        } catch (Exception e){
            printer.print(e);
        }
    }
}
