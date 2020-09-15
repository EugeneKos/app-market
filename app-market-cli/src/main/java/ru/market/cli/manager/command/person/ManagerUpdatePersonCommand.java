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

import static ru.market.cli.manager.ManagerCommandArguments.FIRST_NAME_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.ID_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.LAST_NAME_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.MIDDLE_NAME_ARG;

@Service
public class ManagerUpdatePersonCommand implements Command {
    private PersonRestClient personRestClient;
    private Printer printer;

    @Autowired
    public ManagerUpdatePersonCommand(PersonRestClient personRestClient, Printer printer) {
        this.personRestClient = personRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "person-update";
    }

    @Override
    public String getDescription() {
        return "Команда на изменение информации о пользователе";
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
        printer.printTable(PrinterUtils.createPersonsTableToPrint(Collections.singletonList(updated)));
    }
}
