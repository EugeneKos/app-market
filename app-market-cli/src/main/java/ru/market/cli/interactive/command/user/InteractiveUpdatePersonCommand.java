package ru.market.cli.interactive.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.helper.command.CommandContext;
import ru.market.cli.interactive.helper.command.CommandContext.CommandArgument;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
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
public class InteractiveUpdatePersonCommand extends InteractiveCommonCommand {
    private PersonRestClient personRestClient;
    private CommandHelper commandHelper;
    private CommandContext commandContext;
    private Printer printer;

    @Autowired
    public InteractiveUpdatePersonCommand(PersonRestClient personRestClient,
                                          CommandHelper commandHelper,
                                          CommandContext commandContext,
                                          Printer printer) {

        this.personRestClient = personRestClient;
        this.commandHelper = commandHelper;
        this.commandContext = commandContext;
        this.printer = printer;
    }

    @Override
    public String id() {
        return IDElement.UPDATE_USER_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        PersonDTO personDTO = PersonDTO.personBuilder().build();

        Long userId = commandContext.getCommandArgument(CommandArgument.USER_ID);
        personDTO.setId(userId);

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите фамилию", true, PersonDTO::setFirstName),
                        new CommandDetail<>("Введите имя", true, PersonDTO::setLastName),
                        new CommandDetail<>("Введите отчество", true, PersonDTO::setMiddleName)
                )),
                personDTO
        );

        if(isInterrupted){
            return IDElement.USER_CONTROL_MENU;
        }

        PersonDTO updated = personRestClient.update(personDTO);
        printer.printTable(PrinterUtils.createPersonsTableToPrint(Collections.singletonList(updated)));

        return IDElement.APPLICATION_MENU;
    }

    @Override
    public String getElementIdByException() {
        return IDElement.APPLICATION_MENU;
    }

    @Override
    public String getElementIdByRestClientException() {
        return IDElement.APPLICATION_MENU;
    }
}
