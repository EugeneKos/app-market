package ru.market.cli.command.money;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.utils.Printer;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.dto.money.MoneyAccountDTO;

import java.util.Map;

import static ru.market.cli.command.CommandArguments.ID_ARG;

public class GetMoneyAccountCommand implements Command {
    private MoneyAccountRestClient moneyAccountRestClient;

    public GetMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient) {
        this.moneyAccountRestClient = moneyAccountRestClient;
    }

    @Override
    public String getName() {
        return "money-get";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{ID_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        MoneyAccountDTO moneyAccount = moneyAccountRestClient.getById(Long.parseLong(arguments.get(ID_ARG)));
        Printer.print(moneyAccount);
    }
}
