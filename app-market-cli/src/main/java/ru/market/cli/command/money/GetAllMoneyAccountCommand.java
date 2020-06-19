package ru.market.cli.command.money;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.Printer;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.dto.money.MoneyAccountDTO;

import java.util.Map;
import java.util.Set;

public class GetAllMoneyAccountCommand implements Command {
    private MoneyAccountRestClient moneyAccountRestClient;
    private Printer printer;

    public GetAllMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient, Printer printer) {
        this.moneyAccountRestClient = moneyAccountRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "money-get-all";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[0];
    }

    @Override
    public void perform(Map<Argument, String> map) {
        Set<MoneyAccountDTO> moneyAccounts = moneyAccountRestClient.getAll();
        printer.print(moneyAccounts);
    }
}
