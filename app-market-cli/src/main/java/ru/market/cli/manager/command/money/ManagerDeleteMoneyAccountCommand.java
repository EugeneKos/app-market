package ru.market.cli.manager.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.client.rest.MoneyAccountRestClient;

import java.util.Map;

import static ru.market.cli.manager.ManagerCommandArguments.ID_ARG;

@Service
public class ManagerDeleteMoneyAccountCommand implements Command {
    private MoneyAccountRestClient moneyAccountRestClient;

    @Autowired
    public ManagerDeleteMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient) {
        this.moneyAccountRestClient = moneyAccountRestClient;
    }

    @Override
    public String getName() {
        return "money-delete";
    }

    @Override
    public String getDescription() {
        return "Команда на удаление денежного счета";
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
