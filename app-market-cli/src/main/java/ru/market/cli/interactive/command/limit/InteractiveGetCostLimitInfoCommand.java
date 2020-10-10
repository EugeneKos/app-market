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
import ru.market.dto.limit.CostLimitInfoDTO;

import java.io.BufferedReader;
import java.util.Collections;

@Service
public class InteractiveGetCostLimitInfoCommand extends InteractiveCommonCommand {
    private CostLimitRestClient costLimitRestClient;
    private CommandHelper commandHelper;
    private CommandContext commandContext;
    private Printer printer;

    @Autowired
    public InteractiveGetCostLimitInfoCommand(CostLimitRestClient costLimitRestClient,
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
        return IDElement.GET_COST_LIMIT_INFO_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        Long costLimitId = commandContext.getCommandArgument(CommandArgument.COST_LIMIT_ID);

        TypeWrapper<String> typeWrapperDate = new TypeWrapper<>();

        boolean isInterrupted = InteractiveCommandUtils.fillDateArgument(reader, commandHelper, typeWrapperDate);
        if(isInterrupted){
            return IDElement.COST_LIMIT_COST_MENU;
        }

        CostLimitInfoDTO costLimitInfo = costLimitRestClient.getCostLimitInfoById(costLimitId, typeWrapperDate.getTypeValue());
        printer.printTable(PrinterUtils.createCostLimitInfosTableToPrint(Collections.singletonList(costLimitInfo)));

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
