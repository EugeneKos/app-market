package ru.market.cli.interactive.command.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.helper.command.CommandContext;
import ru.market.cli.interactive.helper.command.CommandContext.CommandArgument;
import ru.market.cli.interactive.command.InteractiveCommandUtils;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.interactive.helper.command.TypeWrapper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.CostRestClient;
import ru.market.dto.cost.CostDTO;

import java.io.BufferedReader;
import java.util.Set;

@Service
public class InteractiveGetAllCostCommand extends InteractiveCommonCommand {
    private CostRestClient costRestClient;
    private CommandHelper commandHelper;
    private CommandContext commandContext;
    private Printer printer;

    @Autowired
    public InteractiveGetAllCostCommand(CostRestClient costRestClient,
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
        return IDElement.GET_ALL_COST_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        Long costLimitId = commandContext.getCommandArgument(CommandArgument.COST_LIMIT_ID);

        Set<CostDTO> costs = costRestClient.getAllByCostLimitId(costLimitId);
        printer.printTable(PrinterUtils.createCostsTableToPrint(costs));

        TypeWrapper<Long> typeWrapperCostId = new TypeWrapper<>();

        boolean isInterrupted = InteractiveCommandUtils.fillCostIdArgument(reader, commandHelper, typeWrapperCostId);
        if(isInterrupted){
            return IDElement.COST_LIMIT_COST_MENU;
        }

        commandContext.putCommandArgument(CommandArgument.COST_ID, typeWrapperCostId.getTypeValue());

        return IDElement.COST_MENU;
    }

    @Override
    public String getElementIdByException() {
        return IDElement.COST_LIMIT_CONTROL_MENU;
    }

    @Override
    public String getElementIdByRestClientException() {
        return IDElement.COST_LIMIT_CONTROL_MENU;
    }
}
