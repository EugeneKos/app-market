package ru.market.cli.interactive.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.UserRestClient;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class InteractiveRegistrationUserCommand implements Command {
    private UserRestClient userRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveRegistrationUserCommand(UserRestClient userRestClient, CommandHelper commandHelper, Printer printer) {
        this.userRestClient = userRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Регистрация нового пользователя";
    }

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        RegistrationDTO registrationDTO = RegistrationDTO.builder().build();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите фамилию", true, RegistrationDTO::setFirstName),
                        new CommandDetail<>("Введите имя", true, RegistrationDTO::setLastName),
                        new CommandDetail<>("Введите отчество", true, RegistrationDTO::setMiddleName),
                        new CommandDetail<>("Введите имя пользователя", true, RegistrationDTO::setUsername),
                        new CommandDetail<>("Введите пароль", true, RegistrationDTO::setPassword)
                )),
                registrationDTO
        );

        if(isInterrupted){
            menu.back(reader);
            return;
        }

        UserDTO user = userRestClient.registration(registrationDTO);
        printer.printTable(PrinterUtils.createUsersTableToPrint(Collections.singletonList(user)));

        menu.back(reader);
    }
}
