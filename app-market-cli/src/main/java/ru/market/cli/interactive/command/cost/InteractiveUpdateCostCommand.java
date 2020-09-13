package ru.market.cli.interactive.command.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.CostRestClient;
import ru.market.dto.cost.CostDTO;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class InteractiveUpdateCostCommand extends InteractiveCommonCommand {
    private CostRestClient costRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveUpdateCostCommand(CostRestClient costRestClient, CommandHelper commandHelper, Printer printer) {
        this.costRestClient = costRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Обновить затрату";
    }

    @Override
    public void perform(BufferedReader reader) {
        CostDTO costDTO = CostDTO.costBuilder().build();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите id затраты",
                                true, (object, param) -> object.setId(Long.parseLong(param))
                        ),
                        new CommandDetail<>("Введите сумму затраты",
                                true, CostDTO::setSum
                        ),
                        new CommandDetail<>("Введите описание затраты",
                                true, CostDTO::setDescription
                        ),
                        new CommandDetail<>("Введите категорию затраты",
                                true, CostDTO::setCategory
                        ),
                        new CommandDetail<>("Введите дату затраты",
                                true, CostDTO::setDateStr
                        ),
                        new CommandDetail<>("Введите вермя затраты",
                                true, CostDTO::setTimeStr
                        ),
                        new CommandDetail<>("Введите id лимита",
                                true, (object, param) -> object.setCostLimitId(Long.parseLong(param))
                        ),
                        new CommandDetail<>("Введите id денежного счета", true,
                                (object, param) -> object.setMoneyAccountId(Long.parseLong(param))
                        )
                )),
                costDTO
        );

        if(isInterrupted){
            return;
        }

        CostDTO updated = costRestClient.update(costDTO);
        printer.printTable(PrinterUtils.createCostsTableToPrint(Collections.singletonList(updated)));
    }
}
