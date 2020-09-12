package ru.market.cli.interactive.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.dto.money.MoneyAccountDTO;

import java.io.BufferedReader;
import java.util.Set;

@Service
public class InteractiveGetAllMoneyAccountCommand implements Command {
    private MoneyAccountRestClient moneyAccountRestClient;
    private Printer printer;

    @Autowired
    public InteractiveGetAllMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient, Printer printer) {
        this.moneyAccountRestClient = moneyAccountRestClient;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Получить все денежные счета";
    }

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        Set<MoneyAccountDTO> moneyAccounts = moneyAccountRestClient.getAll();
        printer.printTable(PrinterUtils.createMoneyAccountsTableToPrint(moneyAccounts));
        menu.back(reader);
    }
}
