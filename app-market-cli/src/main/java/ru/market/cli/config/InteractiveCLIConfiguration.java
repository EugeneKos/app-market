package ru.market.cli.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ru.market.cli.interactive.command.auth.InteractiveAuthenticateCommand;
import ru.market.cli.interactive.command.auth.InteractiveLogoutCommand;
import ru.market.cli.interactive.command.cost.InteractiveCreateCostCommand;
import ru.market.cli.interactive.command.cost.InteractiveDeleteCostCommand;
import ru.market.cli.interactive.command.cost.InteractiveGetAllCostCommand;
import ru.market.cli.interactive.command.cost.InteractiveUpdateCostCommand;
import ru.market.cli.interactive.command.limit.InteractiveCreateCostLimitCommand;
import ru.market.cli.interactive.command.limit.InteractiveDeleteCostLimitCommand;
import ru.market.cli.interactive.command.limit.InteractiveGetAllCostLimitCommand;
import ru.market.cli.interactive.command.limit.InteractiveGetCostLimitInfoCommand;
import ru.market.cli.interactive.command.money.InteractiveCreateMoneyAccountCommand;
import ru.market.cli.interactive.command.money.InteractiveDeleteMoneyAccountCommand;
import ru.market.cli.interactive.command.money.InteractiveGetAllMoneyAccountCommand;
import ru.market.cli.interactive.command.money.InteractiveGetMoneyAccountCommand;
import ru.market.cli.interactive.element.Menu;
import ru.market.cli.interactive.element.impl.MenuImpl;
import ru.market.cli.interactive.helper.command.CommandHelper;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@ComponentScan(basePackages = {"ru.market.cli.interactive.command"})
public class InteractiveCLIConfiguration {
    @Bean
    public CommandHelper commandHelper(){
        return new CommandHelper();
    }

    @Bean
    public Menu authenticateMenu(InteractiveAuthenticateCommand interactiveAuthenticateCommand,
                                 InteractiveLogoutCommand interactiveLogoutCommand){

        MenuImpl authenticateMenu = new MenuImpl("Аутентификация");
        authenticateMenu.setElements(new ArrayList<>(Arrays.asList(
                interactiveAuthenticateCommand, interactiveLogoutCommand
        )));
        return authenticateMenu;
    }

    @Bean
    public Menu costMenu(InteractiveCreateCostCommand interactiveCreateCostCommand,
                         InteractiveDeleteCostCommand interactiveDeleteCostCommand,
                         InteractiveGetAllCostCommand interactiveGetAllCostCommand,
                         InteractiveUpdateCostCommand interactiveUpdateCostCommand){

        MenuImpl costMenu = new MenuImpl("Затраты");
        costMenu.setElements(new ArrayList<>(Arrays.asList(
                interactiveCreateCostCommand, interactiveDeleteCostCommand,
                interactiveGetAllCostCommand, interactiveUpdateCostCommand
        )));
        return costMenu;
    }

    @Bean
    public Menu costLimitMenu(InteractiveCreateCostLimitCommand interactiveCreateCostLimitCommand,
                              InteractiveDeleteCostLimitCommand interactiveDeleteCostLimitCommand,
                              InteractiveGetAllCostLimitCommand interactiveGetAllCostLimitCommand,
                              InteractiveGetCostLimitInfoCommand interactiveGetCostLimitInfoCommand){

        MenuImpl costLimitMenu = new MenuImpl("Лимиты на затраты");
        costLimitMenu.setElements(new ArrayList<>(Arrays.asList(
                interactiveCreateCostLimitCommand, interactiveDeleteCostLimitCommand,
                interactiveGetAllCostLimitCommand, interactiveGetCostLimitInfoCommand
        )));
        return costLimitMenu;
    }

    @Bean
    public Menu moneyAccountMenu(InteractiveCreateMoneyAccountCommand interactiveCreateMoneyAccountCommand,
                                 InteractiveDeleteMoneyAccountCommand interactiveDeleteMoneyAccountCommand,
                                 InteractiveGetAllMoneyAccountCommand interactiveGetAllMoneyAccountCommand,
                                 InteractiveGetMoneyAccountCommand interactiveGetMoneyAccountCommand){

        MenuImpl moneyAccountMenu = new MenuImpl("Денежные счета");
        moneyAccountMenu.setElements(new ArrayList<>(Arrays.asList(
                interactiveCreateMoneyAccountCommand, interactiveDeleteMoneyAccountCommand,
                interactiveGetAllMoneyAccountCommand, interactiveGetMoneyAccountCommand
        )));
        return moneyAccountMenu;
    }

    @Bean
    public Menu mainMenu(@Qualifier("authenticateMenu") Menu authenticateMenu,
                         @Qualifier("moneyAccountMenu") Menu moneyAccountMenu,
                         @Qualifier("costLimitMenu") Menu costLimitMenu,
                         @Qualifier("costMenu") Menu costMenu){

        MenuImpl mainMenu = new MenuImpl("Main");
        mainMenu.setElements(new ArrayList<>(Arrays.asList(
                authenticateMenu, moneyAccountMenu, costLimitMenu, costMenu
        )));
        mainMenu.setBackName("Выход");
        return mainMenu;
    }
}
