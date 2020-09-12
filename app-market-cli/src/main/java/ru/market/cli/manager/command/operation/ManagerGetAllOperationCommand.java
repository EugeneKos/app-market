package ru.market.cli.manager.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.PrinterUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.OperationRestClient;
import ru.market.dto.operation.OperationDTO;

import java.util.Map;
import java.util.Set;

import static ru.market.cli.manager.ManagerCommandArguments.MONEY_ACCOUNT_ID_ARG;

@Service
public class ManagerGetAllOperationCommand implements Command {
    private OperationRestClient operationRestClient;
    private Printer printer;

    @Autowired
    public ManagerGetAllOperationCommand(OperationRestClient operationRestClient, Printer printer) {
        this.operationRestClient = operationRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "operation-get-all";
    }

    @Override
    public String getDescription() {
        return "Команда на получение всех операций";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{MONEY_ACCOUNT_ID_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        Set<OperationDTO> operations = operationRestClient.getAllByMoneyAccountId(
                Long.parseLong(arguments.get(MONEY_ACCOUNT_ID_ARG))
        );

        printer.printTable(PrinterUtils.createOperationsTableToPrint(operations));
    }
}
