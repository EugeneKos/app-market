package ru.market.cli.command.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.command.CommandUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.PersonRestClient;
import ru.market.dto.person.PersonDTO;

import java.util.Collections;
import java.util.Map;

@Service
public class CurrentPersonCommand implements Command {
    private PersonRestClient personRestClient;
    private Printer printer;

    @Autowired
    public CurrentPersonCommand(PersonRestClient personRestClient, Printer printer) {
        this.personRestClient = personRestClient;
        this.printer = printer;
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
    public void perform(Map<Argument, String> arguments) {
        PersonDTO person = personRestClient.getCurrent();
        printer.printTable(CommandUtils.createPersonsTableToPrint(Collections.singletonList(person)));
    }
}
