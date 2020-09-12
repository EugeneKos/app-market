package ru.market.cli.interactive.command.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.CostRestClient;
import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class InteractiveCreateCostCommand implements Command {
    private CostRestClient costRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveCreateCostCommand(CostRestClient costRestClient, CommandHelper commandHelper, Printer printer) {
        this.costRestClient = costRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Создать затрату";
    }

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        CostNoIdDTO costNoIdDTO = CostNoIdDTO.builder().build();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>(
                                "Введите сумму затраты", true, CostNoIdDTO::setSum
                        ),
                        new CommandDetail<>(
                                "Введите описание затраты", true, CostNoIdDTO::setDescription
                        ),
                        new CommandDetail<>(
                                "Введите категорию затраты", true, CostNoIdDTO::setCategory
                        ),
                        new CommandDetail<>(
                                "Введите дату затраты", true, CostNoIdDTO::setDateStr
                        ),
                        new CommandDetail<>(
                                "Введите вермя затраты", true, CostNoIdDTO::setTimeStr
                        ),
                        new CommandDetail<>(
                                "Введите id лимита",
                                true, (object, param) -> object.setCostLimitId(Long.parseLong(param))
                        ),
                        new CommandDetail<>(
                                "Введите id денежного счета",
                                true, (object, param) -> object.setMoneyAccountId(Long.parseLong(param))
                        )
                )),
                costNoIdDTO);

        if(isInterrupted){
            menu.back(reader);
            return;
        }

        CostDTO costDTO = costRestClient.create(costNoIdDTO);
        printer.printTable(PrinterUtils.createCostsTableToPrint(Collections.singletonList(costDTO)));

        menu.back(reader);
    }
}
