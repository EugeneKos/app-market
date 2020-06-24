package ru.market.cli.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.command.CommandUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.dto.money.MoneyAccountDTO;

import java.util.Collections;
import java.util.Map;

import static ru.market.cli.command.CommandArguments.ID_ARG;

@Service
public class GetMoneyAccountCommand implements Command {
    private MoneyAccountRestClient moneyAccountRestClient;
    private Printer printer;

    @Autowired
    public GetMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient, Printer printer) {
        this.moneyAccountRestClient = moneyAccountRestClient;
        this.printer = printer;
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
        printer.printTable(CommandUtils.createMoneyAccountsTableToPrint(Collections.singletonList(moneyAccount)));
    }
}
