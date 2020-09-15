package ru.market.cli.interactive.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommandUtils;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.OperationRestClient;
import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;

import java.io.BufferedReader;
import java.util.Collections;

@Service
public class InteractiveDebitOperationCommand extends InteractiveCommonCommand {
    private OperationRestClient operationRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveDebitOperationCommand(OperationRestClient operationRestClient, CommandHelper commandHelper, Printer printer) {
        this.operationRestClient = operationRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Выполнить операцию списания";
    }

    @Override
    public void perform(BufferedReader reader) {
        OperationEnrollDebitDTO enrollDebitDTO = OperationEnrollDebitDTO.operationEnrollBuilder().build();

        boolean isInterrupted = InteractiveCommandUtils.fillEnrollDebitOperationArguments(reader, commandHelper, enrollDebitDTO);

        if(isInterrupted){
            return;
        }

        OperationDTO operation = operationRestClient.debit(enrollDebitDTO);
        printer.printTable(PrinterUtils.createOperationsTableToPrint(Collections.singletonList(operation)));
    }
}