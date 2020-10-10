package ru.market.cli.interactive.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.helper.command.CommandContext;
import ru.market.cli.interactive.helper.command.CommandContext.CommandArgument;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
import ru.market.client.rest.MoneyAccountRestClient;

import java.io.BufferedReader;

@Service
public class InteractiveDeleteMoneyAccountCommand extends InteractiveCommonCommand {
    private MoneyAccountRestClient moneyAccountRestClient;
    private CommandContext commandContext;

    @Autowired
    public InteractiveDeleteMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient,
                                                CommandContext commandContext) {

        this.moneyAccountRestClient = moneyAccountRestClient;
        this.commandContext = commandContext;
    }

    @Override
    public String id() {
        return IDElement.DELETE_MONEY_ACCOUNT_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        moneyAccountRestClient.deleteById(commandContext.getCommandArgument(CommandArgument.MONEY_ACCOUNT_ID));
        return IDElement.MONEY_ACCOUNT_CONTROL_MENU;
    }

    @Override
    public String getElementIdByException() {
        return IDElement.MONEY_ACCOUNT_CONTROL_MENU;
    }

    @Override
    public String getElementIdByRestClientException() {
        return IDElement.MONEY_ACCOUNT_CONTROL_MENU;
    }
}
