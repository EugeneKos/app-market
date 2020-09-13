package ru.market.cli.interactive.command.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.CostLimitRestClient;
import ru.market.dto.limit.CostLimitDTO;

import java.io.BufferedReader;
import java.util.Set;

@Service
public class InteractiveGetAllCostLimitCommand extends InteractiveCommonCommand {
    private CostLimitRestClient costLimitRestClient;
    private Printer printer;

    @Autowired
    public InteractiveGetAllCostLimitCommand(CostLimitRestClient costLimitRestClient, Printer printer) {
        this.costLimitRestClient = costLimitRestClient;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Получить все лимиты на затраты";
    }

    @Override
    public void perform(BufferedReader reader) {
        Set<CostLimitDTO> costLimits = costLimitRestClient.getAll();
        printer.printTable(PrinterUtils.createCostLimitsTableToPrint(costLimits));
    }
}
