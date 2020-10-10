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
import ru.market.dto.operation.OperationTransferDTO;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class InteractiveTransferOperationCommand extends InteractiveCommonCommand {
    private OperationRestClient operationRestClient;
    private CommandHelper commandHelper;
    private CommandContext commandContext;
    private Printer printer;

    @Autowired
    public InteractiveTransferOperationCommand(OperationRestClient operationRestClient,
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
        return IDElement.TRANSFER_OPERATION_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        OperationTransferDTO transferDTO = OperationTransferDTO.operationTransferBuilder().build();

        Long moneyAccountId = commandContext.getCommandArgument(CommandArgument.MONEY_ACCOUNT_ID);
        transferDTO.setFromMoneyAccountId(moneyAccountId);

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите id счета на который поступят деньги", true,
                                (object, param) -> object.setToMoneyAccountId(Long.parseLong(param))
                        ),
                        new CommandDetail<>("Введите сумму перевода", true,
                                OperationTransferDTO::setAmount
                        ),
                        new CommandDetail<>("Введите дату операции", false,
                                OperationTransferDTO::setDateStr
                        ),
                        new CommandDetail<>("Введите время операции", false,
                                OperationTransferDTO::setTimeStr
                        )
                )),
                transferDTO
        );

        if(isInterrupted){
            return IDElement.MONEY_ACCOUNT_OPERATION_MENU;
        }

        OperationDTO operation = operationRestClient.transfer(transferDTO);
        printer.printTable(PrinterUtils.createOperationsTableToPrint(Collections.singletonList(operation)));

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
