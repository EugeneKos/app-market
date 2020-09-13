package ru.market.cli.interactive.command.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.interactive.helper.command.CommandArgumentWrapper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.CostRestClient;
import ru.market.dto.cost.CostDTO;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

@Service
public class InteractiveGetAllCostCommand extends InteractiveCommonCommand {
    private CostRestClient costRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveGetAllCostCommand(CostRestClient costRestClient, CommandHelper commandHelper, Printer printer) {
        this.costRestClient = costRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Получить все затраты";
    }

    @Override
    public void perform(BufferedReader reader) {
        CommandArgumentWrapper commandArgumentWrapper = new CommandArgumentWrapper();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите id лимита", true,
                                (object, param) -> object.addCommandArgument("idCostLimit", param)
                        ),
                        new CommandDetail<>("Введите дату", true,
                                (object, param) -> object.addCommandArgument("dateCosts", param)
                        )
                )),
                commandArgumentWrapper
        );

        if(isInterrupted){
            return;
        }

        Set<CostDTO> costs = costRestClient.getAllByCostLimitIdAndDate(
                Long.parseLong(commandArgumentWrapper.getCommandArgument("idCostLimit")),
                commandArgumentWrapper.getCommandArgument("dateCosts")
        );
        printer.printTable(PrinterUtils.createCostsTableToPrint(costs));
    }
}
