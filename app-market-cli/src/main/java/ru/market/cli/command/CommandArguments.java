package ru.market.cli.command;

import ru.ed.microlib.command.Argument;

public interface CommandArguments {
    Argument ID_ARG = new Argument("-i", "--id", true);

    Argument FIRST_NAME_ARG = new Argument("-f", "--firstName", true);
    Argument LAST_NAME_ARG = new Argument("-l", "--lastName", true);
    Argument MIDDLE_NAME_ARG = new Argument("-m", "--middleName", true);

    Argument USERNAME_ARG = new Argument("-u", "--username", true);
    Argument PASSWORD_ARG = new Argument("-p", "--password", true);

    Argument BALANCE_ARG = new Argument("-b", "--balance", true);
    Argument NAME_ARG = new Argument("-n", "--name", true);
    Argument DESCRIPTION_ARG = new Argument("-d", "--description", true);
}
