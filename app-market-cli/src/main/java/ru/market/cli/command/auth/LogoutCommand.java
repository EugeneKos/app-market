package ru.market.cli.command.auth;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.Printer;
import ru.market.client.rest.AuthenticateRestClient;

import java.util.Map;

public class LogoutCommand implements Command {
    private AuthenticateRestClient authenticateRestClient;
    private Printer printer;

    public LogoutCommand(AuthenticateRestClient authenticateRestClient, Printer printer) {
        this.authenticateRestClient = authenticateRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "logout";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[0];
    }

    @Override
    public void perform(Map<Argument, String> map) {
        try {
            authenticateRestClient.logout();
        } catch (Exception e){
            printer.print(e);
        }
    }
}
