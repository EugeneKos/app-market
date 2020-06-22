package ru.market.cli.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.client.rest.MoneyAccountRestClient;

import java.util.Map;

import static ru.market.cli.command.CommandArguments.ID_ARG;

@Service
public class DeleteMoneyAccountCommand implements Command {
    private MoneyAccountRestClient moneyAccountRestClient;

    @Autowired
    public DeleteMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient) {
        this.moneyAccountRestClient = moneyAccountRestClient;
    }

    @Override
    public String getName() {
        return "money-delete";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{ID_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        moneyAccountRestClient.deleteById(Long.parseLong(arguments.get(ID_ARG)));
    }
}
