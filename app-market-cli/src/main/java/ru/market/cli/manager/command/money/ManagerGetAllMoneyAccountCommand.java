package ru.market.cli.manager.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.PrinterUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.dto.money.MoneyAccountDTO;

import java.util.Map;
import java.util.Set;

@Service
public class ManagerGetAllMoneyAccountCommand implements Command {
    private MoneyAccountRestClient moneyAccountRestClient;
    private Printer printer;

    @Autowired
    public ManagerGetAllMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient, Printer printer) {
        this.moneyAccountRestClient = moneyAccountRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "money-get-all";
    }

    @Override
    public String getDescription() {
        return "Команда на получение всех денежных счетов";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[0];
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        Set<MoneyAccountDTO> moneyAccounts = moneyAccountRestClient.getAll();
        printer.printTable(PrinterUtils.createMoneyAccountsTableToPrint(moneyAccounts));
    }
}