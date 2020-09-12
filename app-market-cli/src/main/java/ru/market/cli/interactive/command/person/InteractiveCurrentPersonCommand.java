package ru.market.cli.interactive.command.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.PersonRestClient;
import ru.market.dto.person.PersonDTO;

import java.io.BufferedReader;
import java.util.Collections;

@Service
public class InteractiveCurrentPersonCommand implements Command {
    private PersonRestClient personRestClient;
    private Printer printer;

    @Autowired
    public InteractiveCurrentPersonCommand(PersonRestClient personRestClient, Printer printer) {
        this.personRestClient = personRestClient;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Получить информацию о пользователе";
    }

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        PersonDTO person = personRestClient.getCurrent();
        printer.printTable(PrinterUtils.createPersonsTableToPrint(Collections.singletonList(person)));
        menu.back(reader);
    }
}
