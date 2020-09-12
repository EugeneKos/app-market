package ru.market.cli.interactive.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;
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
public class InteractiveTransferOperationCommand implements Command {
    private OperationRestClient operationRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveTransferOperationCommand(OperationRestClient operationRestClient, CommandHelper commandHelper, Printer printer) {
        this.operationRestClient = operationRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Выполнить операцию перевода";
    }

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        OperationTransferDTO transferDTO = OperationTransferDTO.operationTransferBuilder().build();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите id счета с которого произойдет перевод", true,
                                (object, param) -> object.setFromMoneyAccountId(Long.parseLong(param))
                        ),
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
            menu.back(reader);
            return;
        }

        OperationDTO operation = operationRestClient.transfer(transferDTO);
        printer.printTable(PrinterUtils.createOperationsTableToPrint(Collections.singletonList(operation)));
        menu.back(reader);
    }
}
