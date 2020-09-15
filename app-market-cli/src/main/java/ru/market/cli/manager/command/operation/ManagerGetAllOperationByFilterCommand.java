package ru.market.cli.manager.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.PrinterUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.OperationRestClient;
import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationFilterDTO;

import java.util.Map;
import java.util.Set;

import static ru.market.cli.manager.ManagerCommandArguments.AMOUNT_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.DATE_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.DESCRIPTION_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.MONEY_ACCOUNT_ID_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.OPERATION_TYPE_ARG;

@Service
public class ManagerGetAllOperationByFilterCommand implements Command {
    private OperationRestClient operationRestClient;
    private Printer printer;

    @Autowired
    public ManagerGetAllOperationByFilterCommand(OperationRestClient operationRestClient, Printer printer) {
        this.operationRestClient = operationRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "operation-get-all-filter";
    }

    @Override
    public String getDescription() {
        return "Команда на получение всех операций по фильтру";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{MONEY_ACCOUNT_ID_ARG, AMOUNT_ARG, DATE_ARG, DESCRIPTION_ARG, OPERATION_TYPE_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        OperationFilterDTO filterDTO = OperationFilterDTO.builder()
                .amount(arguments.get(AMOUNT_ARG))
                .dateStr(arguments.get(DATE_ARG))
                .description(arguments.get(DESCRIPTION_ARG))
                .operationType(arguments.get(OPERATION_TYPE_ARG))
                .build();

        Set<OperationDTO> operations = operationRestClient.getAllByMoneyAccountIdAndFilter(
                Long.parseLong(arguments.get(MONEY_ACCOUNT_ID_ARG)), filterDTO
        );

        printer.printTable(PrinterUtils.createOperationsTableToPrint(operations));
    }
}