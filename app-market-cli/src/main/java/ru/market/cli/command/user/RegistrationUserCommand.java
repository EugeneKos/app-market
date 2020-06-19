package ru.market.cli.command.user;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.Printer;
import ru.market.client.rest.UserRestClient;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;

import java.util.Map;

public class RegistrationUserCommand implements Command {
    private static final Argument FIRST_NAME_ARG = new Argument("-f", "--firstName", true);
    private static final Argument LAST_NAME_ARG = new Argument("-l", "--lastName", true);
    private static final Argument MIDDLE_NAME_ARG = new Argument("-m", "--middleName", true);
    private static final Argument USERNAME_ARG = new Argument("-u", "--username", true);
    private static final Argument PASSWORD_ARG = new Argument("-p", "--password", true);

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
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setFirstName(arguments.get(FIRST_NAME_ARG));
        registrationDTO.setLastName(arguments.get(LAST_NAME_ARG));
        registrationDTO.setMiddleName(arguments.get(MIDDLE_NAME_ARG));
        registrationDTO.setUsername(arguments.get(USERNAME_ARG));
        registrationDTO.setPassword(arguments.get(PASSWORD_ARG));

        try {
            UserDTO registration = userRestClient.registration(registrationDTO);
            printer.print(registration);
        } catch (Exception e){
            printer.print(e);
        }
    }
}
