package ru.market.cli.interactive.command.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommandUtils;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.helper.command.CommandHelper;
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
    private Printer printer;

    @Autowired
    public InteractiveGetAllCostByDateCommand(CostRestClient costRestClient, CommandHelper commandHelper, Printer printer) {
        this.costRestClient = costRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Получить все затраты по дате";
    }

    @Override
    public void perform(BufferedReader reader) {
        InteractiveCommandUtils.performCommandWithCostLimitIdAndDateArguments(
                reader,
                commandHelper,
                (typeWrapperCostLimitId, typeWrapperDate) -> {
                    Set<CostDTO> costs = costRestClient.getAllByCostLimitIdAndDate(
                            typeWrapperCostLimitId.getTypeValue(), typeWrapperDate.getTypeValue()
                    );
                    printer.printTable(PrinterUtils.createCostsTableToPrint(costs));
                }
        );
    }
}
