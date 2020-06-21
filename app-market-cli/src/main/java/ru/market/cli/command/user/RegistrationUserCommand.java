package ru.market.cli.command.user;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.Printer;
import ru.market.client.rest.UserRestClient;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;

import java.util.Map;

import static ru.market.cli.command.CommandArguments.FIRST_NAME_ARG;
import static ru.market.cli.command.CommandArguments.LAST_NAME_ARG;
import static ru.market.cli.command.CommandArguments.MIDDLE_NAME_ARG;
import static ru.market.cli.command.CommandArguments.PASSWORD_ARG;
import static ru.market.cli.command.CommandArguments.USERNAME_ARG;

public class RegistrationUserCommand implements Command {
    private UserRestClient userRestClient;
    private Printer printer;

    public RegistrationUserCommand(UserRestClient userRestClient, Printer printer) {
        this.userRestClient = userRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "registration";
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

        UserDTO registration = userRestClient.registration(registrationDTO);
        printer.print(registration);
    }
}
