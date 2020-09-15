package ru.market.cli.interactive.command.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommandUtils;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.helper.command.CommandHelper;
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
    private Printer printer;

    @Autowired
    public InteractiveGetCostLimitInfoCommand(CostLimitRestClient costLimitRestClient, CommandHelper commandHelper, Printer printer) {
        this.costLimitRestClient = costLimitRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Получить подробную информацию о лимите";
    }

    @Override
    public void perform(BufferedReader reader) {
        InteractiveCommandUtils.performCommandWithCostLimitIdAndDateArguments(
                reader,
                commandHelper,
                (typeWrapperCostLimitId, typeWrapperDate) -> {
                    CostLimitInfoDTO costLimitInfo = costLimitRestClient.getCostLimitInfoById(
                            typeWrapperCostLimitId.getTypeValue(), typeWrapperDate.getTypeValue()
                    );
                    printer.printTable(PrinterUtils.createCostLimitInfosTableToPrint(Collections.singletonList(costLimitInfo)));
                }
        );
    }
}
