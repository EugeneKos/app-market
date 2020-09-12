package ru.market.cli.manager.command.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.PrinterUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.PersonRestClient;
import ru.market.dto.person.PersonDTO;

import java.util.Collections;
import java.util.Map;

@Service
public class ManagerCurrentPersonCommand implements Command {
    private PersonRestClient personRestClient;
    private Printer printer;

    @Autowired
    public ManagerCurrentPersonCommand(PersonRestClient personRestClient, Printer printer) {
        this.personRestClient = personRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "person-get";
    }

    @Override
    public String getDescription() {
        return "Команда на получение информации о пользователе";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[0];
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        PersonDTO person = personRestClient.getCurrent();
        printer.printTable(PrinterUtils.createPersonsTableToPrint(Collections.singletonList(person)));
    }
}
