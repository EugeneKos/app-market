package ru.market.cli.interactive.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.helper.command.CommandContext;
import ru.market.cli.interactive.helper.command.CommandContext.CommandArgument;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.OperationRestClient;
import ru.market.dto.operation.OperationDTO;

import java.io.BufferedReader;
import java.util.Set;

@Service
public class InteractiveGetAllOperationCommand extends InteractiveCommonCommand {
    private OperationRestClient operationRestClient;
    private CommandContext commandContext;
    private Printer printer;

    @Autowired
    public InteractiveGetAllOperationCommand(OperationRestClient operationRestClient,
                                             CommandContext commandContext,
                                             Printer printer) {

        this.operationRestClient = operationRestClient;
        this.commandContext = commandContext;
        this.printer = printer;
    }

    @Override
    public String id() {
        return IDElement.GET_ALL_OPERATION_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        Long moneyAccountId = commandContext.getCommandArgument(CommandArgument.MONEY_ACCOUNT_ID);

        Set<OperationDTO> operations = operationRestClient.getAllByMoneyAccountId(moneyAccountId);
        printer.printTable(PrinterUtils.createOperationsTableToPrint(operations));

        return IDElement.MONEY_ACCOUNT_OPERATION_MENU;
    }

    @Override
    public String getElementIdByException() {
        return IDElement.MONEY_ACCOUNT_CONTROL_MENU;
    }

    @Override
    public String getElementIdByRestClientException() {
        return IDElement.MONEY_ACCOUNT_CONTROL_MENU;
    }
}
