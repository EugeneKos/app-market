package ru.market.cli.interactive.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommandUtils;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.helper.command.TypeWrapper;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.OperationRestClient;
import ru.market.dto.operation.OperationDTO;

import java.io.BufferedReader;
import java.util.Set;

@Service
public class InteractiveGetAllOperationCommand extends InteractiveCommonCommand {
    private OperationRestClient operationRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveGetAllOperationCommand(OperationRestClient operationRestClient, CommandHelper commandHelper, Printer printer) {
        this.operationRestClient = operationRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Получить все операции";
    }

    @Override
    public void perform(BufferedReader reader) {
        TypeWrapper<Long> typeWrapper = new TypeWrapper<>();

        boolean isInterrupted = InteractiveCommandUtils.fillMoneyAccountIdArgument(reader, commandHelper, typeWrapper);

        if(isInterrupted){
            return;
        }

        Set<OperationDTO> operations = operationRestClient.getAllByMoneyAccountId(typeWrapper.getTypeValue());
        printer.printTable(PrinterUtils.createOperationsTableToPrint(operations));
    }
}
