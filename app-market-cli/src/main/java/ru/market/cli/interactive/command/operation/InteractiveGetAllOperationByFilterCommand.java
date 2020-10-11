package ru.market.cli.interactive.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.helper.command.CommandContext;
import ru.market.cli.interactive.helper.command.CommandContext.CommandArgument;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
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
import java.util.Set;

@Service
public class InteractiveGetAllOperationByFilterCommand extends InteractiveCommonCommand {
    private OperationRestClient operationRestClient;
    private CommandHelper commandHelper;
    private CommandContext commandContext;
    private Printer printer;

    @Autowired
    public InteractiveGetAllOperationByFilterCommand(OperationRestClient operationRestClient,
                                                     CommandHelper commandHelper,
                                                     CommandContext commandContext,
                                                     Printer printer) {

        this.operationRestClient = operationRestClient;
        this.commandHelper = commandHelper;
        this.commandContext = commandContext;
        this.printer = printer;
    }

    @Override
    public String id() {
        return IDElement.GET_ALL_OPERATION_BY_FILTER_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        OperationFilterDTO filterDTO = OperationFilterDTO.builder().build();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
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
            return IDElement.MONEY_ACCOUNT_OPERATION_MENU;
        }

        Long moneyAccountId = commandContext.getCommandArgument(CommandArgument.MONEY_ACCOUNT_ID);

        Set<OperationDTO> operations = operationRestClient.getAllByMoneyAccountIdAndFilter(moneyAccountId, filterDTO);
        printer.printTable(PrinterUtils.createOperationsTableToPrint(operations));

        return IDElement.MONEY_ACCOUNT_OPERATION_MENU;
    }

    @Override
    public String getElementIdByException() {
        return IDElement.MONEY_ACCOUNT_OPERATION_MENU;
    }

    @Override
    public String getElementIdByRestClientException() {
        return IDElement.MONEY_ACCOUNT_OPERATION_MENU;
    }
}
