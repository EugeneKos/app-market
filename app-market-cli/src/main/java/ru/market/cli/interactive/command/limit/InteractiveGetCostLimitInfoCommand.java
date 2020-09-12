package ru.market.cli.interactive.command.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;
import ru.market.cli.interactive.helper.command.CommandArgumentWrapper;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.CostLimitRestClient;
import ru.market.dto.limit.CostLimitInfoDTO;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class InteractiveGetCostLimitInfoCommand implements Command {
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
    public void perform(BufferedReader reader, Menu menu) {
        CommandArgumentWrapper commandArgumentWrapper = new CommandArgumentWrapper();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите id лимита", true,
                                (object, param) -> object.addCommandArgument("idCostLimit", param)
                        ),
                        new CommandDetail<>("Введите дату", true,
                                (object, param) -> object.addCommandArgument("date", param)
                        )
                )),
                commandArgumentWrapper
        );

        if(isInterrupted){
            menu.back(reader);
            return;
        }

        CostLimitInfoDTO costLimitInfo = costLimitRestClient.getCostLimitInfoById(
                Long.parseLong(commandArgumentWrapper.getCommandArgument("idCostLimit")),
                commandArgumentWrapper.getCommandArgument("date")
        );

        printer.printTable(PrinterUtils.createCostLimitInfosTableToPrint(Collections.singletonList(costLimitInfo)));
        menu.back(reader);
    }
}
