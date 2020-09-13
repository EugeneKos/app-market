package ru.market.cli.interactive.command.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.helper.command.TypeWrapper;
import ru.market.cli.interactive.helper.command.CommandDetail;
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
        TypeWrapper<Long> typeWrapperIdCostLimit = new TypeWrapper<>();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                Collections.singletonList(
                        new CommandDetail<>("Введите id лимита", true,
                                (object, param) -> object.setTypeValue(Long.parseLong(param))
                        )
                ),
                typeWrapperIdCostLimit
        );

        if(isInterrupted){
            return;
        }

        TypeWrapper<String> typeWrapperDate = new TypeWrapper<>();

        isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                Collections.singletonList(
                        new CommandDetail<>("Введите дату", true, TypeWrapper::setTypeValue)
                ),
                typeWrapperDate
        );

        if(isInterrupted){
            return;
        }

        CostLimitInfoDTO costLimitInfo = costLimitRestClient.getCostLimitInfoById(
                typeWrapperIdCostLimit.getTypeValue(), typeWrapperDate.getTypeValue()
        );

        printer.printTable(PrinterUtils.createCostLimitInfosTableToPrint(Collections.singletonList(costLimitInfo)));
    }
}
