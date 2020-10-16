package ru.market.cli.interactive.command.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.CostLimitRestClient;
import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class InteractiveCreateCostLimitCommand extends InteractiveCommonCommand {
    private CostLimitRestClient costLimitRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveCreateCostLimitCommand(CostLimitRestClient costLimitRestClient, CommandHelper commandHelper, Printer printer) {
        this.costLimitRestClient = costLimitRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String id() {
        return IDElement.CREATE_COST_LIMIT_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        CostLimitNoIdDTO costLimitNoIdDTO = CostLimitNoIdDTO.builder().build();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите сумму лимита", true, CostLimitNoIdDTO::setSum),
                        new CommandDetail<>("Введите описание лимита", true, CostLimitNoIdDTO::setDescription),
                        new CommandDetail<>("Введите дату начала лимита", true, CostLimitNoIdDTO::setBeginDateStr),
                        new CommandDetail<>("Введите дату окончания", true, CostLimitNoIdDTO::setEndDateStr)
                )),
                costLimitNoIdDTO
        );

        if(isInterrupted){
            return IDElement.COST_LIMIT_CONTROL_MENU;
        }

        CostLimitDTO costLimitDTO = costLimitRestClient.create(costLimitNoIdDTO);
        printer.printTable(PrinterUtils.createCostLimitsTableToPrint(Collections.singletonList(costLimitDTO)));

        return IDElement.COST_LIMIT_CONTROL_MENU;
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
