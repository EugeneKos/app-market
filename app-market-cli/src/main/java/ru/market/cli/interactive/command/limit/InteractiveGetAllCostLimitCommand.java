package ru.market.cli.interactive.command.limit;

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
import ru.market.client.rest.CostLimitRestClient;
import ru.market.dto.limit.CostLimitDTO;

import java.io.BufferedReader;
import java.util.Set;

@Service
public class InteractiveGetAllCostLimitCommand extends InteractiveCommonCommand {
    private CostLimitRestClient costLimitRestClient;
    private CommandHelper commandHelper;
    private CommandContext commandContext;
    private Printer printer;

    @Autowired
    public InteractiveGetAllCostLimitCommand(CostLimitRestClient costLimitRestClient,
                                             CommandHelper commandHelper,
                                             CommandContext commandContext,
                                             Printer printer) {

        this.costLimitRestClient = costLimitRestClient;
        this.commandHelper = commandHelper;
        this.commandContext = commandContext;
        this.printer = printer;
    }

    @Override
    public String id() {
        return IDElement.GET_ALL_COST_LIMIT_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        Set<CostLimitDTO> costLimits = costLimitRestClient.getAll();
        printer.printTable(PrinterUtils.createCostLimitsTableToPrint(costLimits));

        TypeWrapper<Long> typeWrapper = new TypeWrapper<>();

        boolean isInterrupted = InteractiveCommandUtils.fillCostLimitIdArgument(reader, commandHelper, typeWrapper);
        if(isInterrupted){
            return IDElement.COST_LIMIT_CONTROL_MENU;
        }

        Long costLimitId = typeWrapper.getTypeValue();
        if(InteractiveCommandUtils.isNotBelong(costLimitId, costLimits, CostLimitDTO::getId)){
            Printer.error("Неправильный идентификатор лимита на затраты!", InteractiveGetAllCostLimitCommand.class);
            return IDElement.COST_LIMIT_CONTROL_MENU;
        }

        commandContext.putCommandArgument(CommandArgument.COST_LIMIT_ID, costLimitId);

        return IDElement.COST_LIMIT_COST_MENU;
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
