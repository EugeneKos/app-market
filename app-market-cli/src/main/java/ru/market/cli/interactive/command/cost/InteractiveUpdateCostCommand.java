package ru.market.cli.interactive.command.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.helper.command.CommandContext;
import ru.market.cli.interactive.helper.command.CommandContext.CommandArgument;
import ru.market.cli.interactive.command.InteractiveCommandUtils;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.CostRestClient;
import ru.market.dto.cost.CostDTO;

import java.io.BufferedReader;
import java.util.Collections;

@Service
public class InteractiveUpdateCostCommand extends InteractiveCommonCommand {
    private CostRestClient costRestClient;
    private CommandHelper commandHelper;
    private CommandContext commandContext;
    private Printer printer;

    @Autowired
    public InteractiveUpdateCostCommand(CostRestClient costRestClient,
                                        CommandHelper commandHelper,
                                        CommandContext commandContext,
                                        Printer printer) {

        this.costRestClient = costRestClient;
        this.commandHelper = commandHelper;
        this.commandContext = commandContext;
        this.printer = printer;
    }

    @Override
    public String id() {
        return IDElement.UPDATE_COST_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        CostDTO costDTO = CostDTO.costBuilder().build();

        Long costId = commandContext.getCommandArgument(CommandArgument.COST_ID);
        Long costLimitId = commandContext.getCommandArgument(CommandArgument.COST_LIMIT_ID);

        costDTO.setId(costId);
        costDTO.setCostLimitId(costLimitId);

        boolean isInterrupted = InteractiveCommandUtils.fillCreateUpdateCostArgument(reader, commandHelper, costDTO);
        if(isInterrupted){
            return IDElement.COST_MENU;
        }

        CostDTO updated = costRestClient.update(costDTO);
        printer.printTable(PrinterUtils.createCostsTableToPrint(Collections.singletonList(updated)));

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
