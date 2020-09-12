package ru.market.cli.interactive.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;
import ru.market.cli.interactive.helper.command.CommandArgumentWrapper;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.OperationRestClient;
import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationFilterDTO;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

@Service
public class InteractiveGetAllOperationByFilterCommand implements Command {
    private OperationRestClient operationRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveGetAllOperationByFilterCommand(OperationRestClient operationRestClient, CommandHelper commandHelper, Printer printer) {
        this.operationRestClient = operationRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Получить все операции по фильтру";
    }

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        CommandArgumentWrapper commandArgumentWrapper = new CommandArgumentWrapper();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                Collections.singletonList(
                        new CommandDetail<>("Введите id денежного счета", true,
                                (object, param) -> object.addCommandArgument("moneyAccountId", param)
                        )
                ),
                commandArgumentWrapper
        );

        if(isInterrupted){
            menu.back(reader);
            return;
        }

        OperationFilterDTO filterDTO = OperationFilterDTO.builder().build();

        isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите сумму операции", false,
                                OperationFilterDTO::setAmount
                        ),
                        new CommandDetail<>("Введите дату операции", false,
                                OperationFilterDTO::setDateStr
                        ),
                        new CommandDetail<>("Введите описание операции", false,
                                OperationFilterDTO::setDescription
                        ),
                        new CommandDetail<>("Введите тип операции", false,
                                OperationFilterDTO::setOperationType
                        )
                )),
                filterDTO
        );

        if(isInterrupted){
            menu.back(reader);
            return;
        }

        Set<OperationDTO> operations = operationRestClient.getAllByMoneyAccountIdAndFilter(
                Long.parseLong(commandArgumentWrapper.getCommandArgument("moneyAccountId")), filterDTO
        );

        printer.printTable(PrinterUtils.createOperationsTableToPrint(operations));
        menu.back(reader);
    }
}
