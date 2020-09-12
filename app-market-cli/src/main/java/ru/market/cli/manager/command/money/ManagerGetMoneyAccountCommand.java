package ru.market.cli.manager.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.PrinterUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.dto.money.MoneyAccountDTO;

import java.util.Collections;
import java.util.Map;

import static ru.market.cli.manager.ManagerCommandArguments.ID_ARG;

@Service
public class ManagerGetMoneyAccountCommand implements Command {
    private MoneyAccountRestClient moneyAccountRestClient;
    private Printer printer;

    @Autowired
    public ManagerGetMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient, Printer printer) {
        this.moneyAccountRestClient = moneyAccountRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "money-get";
    }

    @Override
    public String getDescription() {
        return "Команда на получение денежного счета";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{ID_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        MoneyAccountDTO moneyAccount = moneyAccountRestClient.getById(Long.parseLong(arguments.get(ID_ARG)));
        printer.printTable(PrinterUtils.createMoneyAccountsTableToPrint(Collections.singletonList(moneyAccount)));
    }
}
