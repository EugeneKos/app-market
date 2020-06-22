package ru.market.cli.command.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.utils.Printer;
import ru.market.client.rest.PersonRestClient;
import ru.market.dto.person.PersonDTO;

import java.util.Map;

@Service
public class CurrentPersonCommand implements Command {
    private PersonRestClient personRestClient;

    @Autowired
    public CurrentPersonCommand(PersonRestClient personRestClient) {
        this.personRestClient = personRestClient;
    }

    @Override
    public String getName() {
        return "person-get";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[0];
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        PersonDTO person = personRestClient.getCurrent();
        Printer.print(person);
    }
}
