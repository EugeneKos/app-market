package ru.market.cli.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.utils.Printer;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.dto.money.MoneyAccountDTO;

import java.util.Map;
import java.util.Set;

@Service
public class GetAllMoneyAccountCommand implements Command {
    private MoneyAccountRestClient moneyAccountRestClient;

    @Autowired
    public GetAllMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient) {
        this.moneyAccountRestClient = moneyAccountRestClient;
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
    public void perform(Map<Argument, String> arguments) {
        Set<MoneyAccountDTO> moneyAccounts = moneyAccountRestClient.getAll();
        Printer.print(moneyAccounts);
    }
}