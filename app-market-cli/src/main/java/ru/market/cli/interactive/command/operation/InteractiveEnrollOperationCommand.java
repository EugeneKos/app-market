package ru.market.cli.interactive.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.helper.command.CommandContext;
import ru.market.cli.interactive.helper.command.CommandContext.CommandArgument;
import ru.market.cli.interactive.command.InteractiveCommandUtils;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.OperationRestClient;
import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;

import java.io.BufferedReader;
import java.util.Collections;

@Service
public class InteractiveEnrollOperationCommand extends InteractiveCommonCommand {
    private OperationRestClient operationRestClient;
    private CommandHelper commandHelper;
    private CommandContext commandContext;
    private Printer printer;

    @Autowired
    public InteractiveEnrollOperationCommand(OperationRestClient operationRestClient,
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
        return IDElement.ENROLL_OPERATION_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        OperationEnrollDebitDTO enrollDebitDTO = OperationEnrollDebitDTO.operationEnrollBuilder().build();

        boolean isInterrupted = InteractiveCommandUtils.fillEnrollDebitOperationArguments(reader, commandHelper, enrollDebitDTO);

        if(isInterrupted){
            return IDElement.MONEY_ACCOUNT_OPERATION_MENU;
        }

        Long moneyAccountId = commandContext.getCommandArgument(CommandArgument.MONEY_ACCOUNT_ID);
        enrollDebitDTO.setMoneyAccountId(moneyAccountId);

        OperationDTO operation = operationRestClient.enrollment(enrollDebitDTO);
        printer.printTable(PrinterUtils.createOperationsTableToPrint(Collections.singletonList(operation)));

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
