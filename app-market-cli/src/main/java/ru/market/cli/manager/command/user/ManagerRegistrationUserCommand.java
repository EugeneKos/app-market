package ru.market.cli.manager.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.PrinterUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.UserRestClient;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;

import java.util.Collections;
import java.util.Map;

import static ru.market.cli.manager.ManagerCommandArguments.FIRST_NAME_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.LAST_NAME_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.MIDDLE_NAME_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.PASSWORD_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.USERNAME_ARG;

@Service
public class ManagerRegistrationUserCommand implements Command {
    private UserRestClient userRestClient;
    private Printer printer;

    @Autowired
    public ManagerRegistrationUserCommand(UserRestClient userRestClient, Printer printer) {
        this.userRestClient = userRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "user-reg";
    }

    @Override
    public String getDescription() {
        return "Команда на регистрацию нового пользователя";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{FIRST_NAME_ARG, LAST_NAME_ARG, MIDDLE_NAME_ARG, USERNAME_ARG, PASSWORD_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        RegistrationDTO registrationDTO = RegistrationDTO.builder()
                .firstName(arguments.get(FIRST_NAME_ARG))
                .lastName(arguments.get(LAST_NAME_ARG))
                .middleName(arguments.get(MIDDLE_NAME_ARG))
                .username(arguments.get(USERNAME_ARG))
                .password(arguments.get(PASSWORD_ARG))
                .build();

        UserDTO user = userRestClient.registration(registrationDTO);
        printer.printTable(PrinterUtils.createUsersTableToPrint(Collections.singletonList(user)));
    }
}
