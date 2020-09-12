package ru.market.cli.interactive.command.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.PersonRestClient;
import ru.market.dto.person.PersonDTO;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class InteractiveUpdatePersonCommand implements Command {
    private PersonRestClient personRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveUpdatePersonCommand(PersonRestClient personRestClient, CommandHelper commandHelper, Printer printer) {
        this.personRestClient = personRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Обновить информацию о пользователе";
    }

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        PersonDTO personDTO = PersonDTO.personBuilder().build();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите id пользователя", true,
                                (object, param) -> object.setId(Long.parseLong(param))
                        ),
                        new CommandDetail<>("Введите фамилию", true, PersonDTO::setFirstName),
                        new CommandDetail<>("Введите имя", true, PersonDTO::setLastName),
                        new CommandDetail<>("Введите отчество", true, PersonDTO::setMiddleName)
                )),
                personDTO
        );

        if(isInterrupted){
            menu.back(reader);
            return;
        }

        PersonDTO updated = personRestClient.update(personDTO);
        printer.printTable(PrinterUtils.createPersonsTableToPrint(Collections.singletonList(updated)));
        menu.back(reader);
    }
}
