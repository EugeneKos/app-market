package ru.market.cli.interactive.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.OperationRestClient;
import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class InteractiveEnrollOperationCommand extends InteractiveCommonCommand {
    private OperationRestClient operationRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveEnrollOperationCommand(OperationRestClient operationRestClient, CommandHelper commandHelper, Printer printer) {
        this.operationRestClient = operationRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Выполнить операцию зачисления";
    }

    @Override
    public void perform(BufferedReader reader) {
        OperationEnrollDebitDTO enrollDebitDTO = OperationEnrollDebitDTO.operationEnrollBuilder().build();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите сумму операции", true,
                                OperationEnrollDebitDTO::setAmount
                        ),
                        new CommandDetail<>("Введите описание операции", true,
                                OperationEnrollDebitDTO::setDescription
                        ),
                        new CommandDetail<>("Введите id денежного счета", true,
                                (object, param) -> object.setMoneyAccountId(Long.parseLong(param))
                        ),
                        new CommandDetail<>("Введите дату операции", false,
                                OperationEnrollDebitDTO::setDateStr
                        ),
                        new CommandDetail<>("Введите время операции", false,
                                OperationEnrollDebitDTO::setTimeStr
                        )
                )),
                enrollDebitDTO
        );

        if(isInterrupted){
            return;
        }

        OperationDTO operation = operationRestClient.enrollment(enrollDebitDTO);
        printer.printTable(PrinterUtils.createOperationsTableToPrint(Collections.singletonList(operation)));
    }
}
