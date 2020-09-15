package ru.market.cli.manager.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.PrinterUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.OperationRestClient;
import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationTransferDTO;

import java.util.Collections;
import java.util.Map;

import static ru.market.cli.manager.ManagerCommandArguments.DATE_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.FROM_MONEY_ACCOUNT_ID_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.MANDATORY_AMOUNT_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.TIME_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.TO_MONEY_ACCOUNT_ID_ARG;

@Service
public class ManagerTransferOperationCommand implements Command {
    private OperationRestClient operationRestClient;
    private Printer printer;

    @Autowired
    public ManagerTransferOperationCommand(OperationRestClient operationRestClient, Printer printer) {
        this.operationRestClient = operationRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "operation-transfer";
    }

    @Override
    public String getDescription() {
        return "Команда на операцию перевода";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{FROM_MONEY_ACCOUNT_ID_ARG, TO_MONEY_ACCOUNT_ID_ARG, MANDATORY_AMOUNT_ARG, DATE_ARG, TIME_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        OperationTransferDTO transferDTO = OperationTransferDTO.operationTransferBuilder()
                .fromMoneyAccountId(Long.parseLong(arguments.get(FROM_MONEY_ACCOUNT_ID_ARG)))
                .toMoneyAccountId(Long.parseLong(arguments.get(TO_MONEY_ACCOUNT_ID_ARG)))
                .build();

        transferDTO.setAmount(arguments.get(MANDATORY_AMOUNT_ARG));
        transferDTO.setDateStr(arguments.get(DATE_ARG));
        transferDTO.setTimeStr(arguments.get(TIME_ARG));

        OperationDTO operation = operationRestClient.transfer(transferDTO);
        printer.printTable(PrinterUtils.createOperationsTableToPrint(Collections.singletonList(operation)));
    }
}
