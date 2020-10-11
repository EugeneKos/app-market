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
public class InteractiveGetAllCostByDateCommand extends InteractiveCommonCommand {
    private CostRestClient costRestClient;
    private CommandHelper commandHelper;
    private CommandContext commandContext;
    private Printer printer;

    @Autowired
    public InteractiveGetAllCostByDateCommand(CostRestClient costRestClient,
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
        return IDElement.GET_ALL_COST_BY_DATE_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        Long costLimitId = commandContext.getCommandArgument(CommandArgument.COST_LIMIT_ID);

        TypeWrapper<String> typeWrapperDate = new TypeWrapper<>();

        boolean isInterrupted = InteractiveCommandUtils.fillDateArgument(reader, commandHelper, typeWrapperDate);
        if(isInterrupted){
            return IDElement.COST_LIMIT_COST_MENU;
        }

        Set<CostDTO> costs = costRestClient.getAllByCostLimitIdAndDate(costLimitId, typeWrapperDate.getTypeValue());
        printer.printTable(PrinterUtils.createCostsTableToPrint(costs));

        TypeWrapper<Long> typeWrapperCostId = new TypeWrapper<>();

        isInterrupted = InteractiveCommandUtils.fillCostIdArgument(reader, commandHelper, typeWrapperCostId);
        if(isInterrupted){
            return IDElement.COST_LIMIT_COST_MENU;
        }

        Long costId = typeWrapperCostId.getTypeValue();
        if(InteractiveCommandUtils.isNotBelong(costId, costs, CostDTO::getId)){
            Printer.error("Неправильный идентификатор затраты!", InteractiveGetAllCostByDateCommand.class);
            return IDElement.COST_LIMIT_COST_MENU;
        }

        commandContext.putCommandArgument(CommandArgument.COST_ID, costId);

        return IDElement.COST_MENU;
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
