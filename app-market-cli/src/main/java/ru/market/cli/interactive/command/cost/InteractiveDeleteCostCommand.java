package ru.market.cli.interactive.command.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.helper.command.CommandContext;
import ru.market.cli.interactive.helper.command.CommandContext.CommandArgument;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
import ru.market.client.rest.CostRestClient;

import java.io.BufferedReader;

@Service
public class InteractiveDeleteCostCommand extends InteractiveCommonCommand {
    private CostRestClient costRestClient;
    private CommandContext commandContext;

    @Autowired
    public InteractiveDeleteCostCommand(CostRestClient costRestClient, CommandContext commandContext) {

        this.costRestClient = costRestClient;
        this.commandContext = commandContext;
    }

    @Override
    public String id() {
        return IDElement.DELETE_COST_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        Long costId = commandContext.getCommandArgument(CommandArgument.COST_ID);
        costRestClient.deleteById(costId);
        return IDElement.COST_LIMIT_COST_MENU;
    }

    @Override
    public String getElementIdByException() {
        return IDElement.COST_LIMIT_COST_MENU;
    }

    @Override
    public String getElementIdByRestClientException() {
        return IDElement.COST_LIMIT_COST_MENU;
    }
}
