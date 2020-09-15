package ru.market.cli.interactive.helper.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class CommandHelper {
    private static final String CMD_ARG_EXIT = "/q";

    public <T> boolean fillBusinessObjectByCommandDetail(BufferedReader reader,
                                                         List<CommandDetail<T>> commandDetails,
                                                         T object){

        try {
            for (CommandDetail<T> detail : commandDetails){
                String cmdArgumentName = detail.getCmdArgumentName() + (detail.isMandatory() ? ": " : " (необязательно) : ");

                String cmdArgument;

                do {
                    System.out.print(cmdArgumentName);
                    cmdArgument = reader.readLine();
                    if(CMD_ARG_EXIT.equals(cmdArgument)){
                        return true;
                    }
                } while (detail.isMandatory() && (cmdArgument == null || cmdArgument.equals("")));

                if(cmdArgument != null && !"".equals(cmdArgument)){
                    detail.getObjectConsumer().accept(object, cmdArgument);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
