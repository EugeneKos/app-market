package ru.market.cli.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ru.market.cli.interactive.element.Element;
import ru.market.cli.interactive.element.ElementStorage;
import ru.market.cli.interactive.element.IDElement;
import ru.market.cli.interactive.element.impl.ElementStorageImpl;
import ru.market.cli.interactive.element.impl.Menu;
import ru.market.cli.interactive.element.impl.MenuItem;
import ru.market.cli.interactive.helper.command.CommandContext;
import ru.market.cli.interactive.helper.command.CommandHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"ru.market.cli.interactive.command"})
public class InteractiveCLIConfiguration {
    @Bean
    public CommandHelper commandHelper(){
        return new CommandHelper();
    }

    @Bean
    public CommandContext commandContext(){
        return new CommandContext();
    }

    @Bean
    public Menu authenticateRegistrationMenu(){
        LinkedHashMap<Integer, MenuItem> menuItemMap = new LinkedHashMap<>();
        menuItemMap.put(1, new MenuItem(IDElement.REGISTRATION_CMD, 1, "Регистрация"));
        menuItemMap.put(2, new MenuItem(IDElement.AUTHENTICATE_CMD, 2, "Аутентификация"));
        menuItemMap.put(0, new MenuItem(IDElement.EXIT_CMD, 0, "Выход"));

        return new Menu(IDElement.MAIN_MENU, menuItemMap);
    }

    @Bean
    public Menu commonApplicationMenu(){
        LinkedHashMap<Integer, MenuItem> menuItemMap = new LinkedHashMap<>();
        menuItemMap.put(1, new MenuItem(IDElement.MONEY_ACCOUNT_CONTROL_MENU, 1, "Управление денежными средствами"));
        menuItemMap.put(2, new MenuItem(IDElement.COST_LIMIT_CONTROL_MENU, 2, "Управление лимитами на затраты"));
        menuItemMap.put(3, new MenuItem(IDElement.GET_USER_CMD, 3, "Пользовательская информация"));
        menuItemMap.put(0, new MenuItem(IDElement.LOGOUT_CMD, 0, "Завершить сеанс"));

        return new Menu(IDElement.APPLICATION_MENU, menuItemMap);
    }

    @Bean
    public Menu moneyAccountControlMenu(){
        LinkedHashMap<Integer, MenuItem> menuItemMap = new LinkedHashMap<>();
        menuItemMap.put(1, new MenuItem(IDElement.CREATE_MONEY_ACCOUNT_CMD, 1, "Создать новый денежный счет"));
        menuItemMap.put(2, new MenuItem(IDElement.GET_ALL_MONEY_ACCOUNT_CMD, 2, "Посмотреть все денежные счета"));
        menuItemMap.put(0, new MenuItem(IDElement.APPLICATION_MENU, 0, "Назад"));

        return new Menu(IDElement.MONEY_ACCOUNT_CONTROL_MENU, menuItemMap);
    }

    @Bean
    public Menu moneyAccountOperationMenu(){
        LinkedHashMap<Integer, MenuItem> menuItemMap = new LinkedHashMap<>();
        menuItemMap.put(1, new MenuItem(IDElement.GET_MONEY_ACCOUNT_CMD, 1, "Получить информацию по счету"));
        menuItemMap.put(2, new MenuItem(IDElement.DEBIT_OPERATION_CMD, 2, "Выполнить операцию списания"));
        menuItemMap.put(3, new MenuItem(IDElement.ENROLL_OPERATION_CMD, 3, "Выполнить операцию зачисления"));
        menuItemMap.put(4, new MenuItem(IDElement.TRANSFER_OPERATION_CMD, 4, "Выполнить операцию перевода"));
        menuItemMap.put(5, new MenuItem(IDElement.GET_ALL_OPERATION_CMD, 5, "Получить все операции"));
        menuItemMap.put(6, new MenuItem(IDElement.GET_ALL_OPERATION_BY_FILTER_CMD, 6, "Получить все операции по фильтру"));
        menuItemMap.put(7, new MenuItem(IDElement.DELETE_MONEY_ACCOUNT_CMD, 7, "Удалить этот денежный счет"));
        menuItemMap.put(0, new MenuItem(IDElement.MONEY_ACCOUNT_CONTROL_MENU, 0, "Назад"));

        return new Menu(IDElement.MONEY_ACCOUNT_OPERATION_MENU, menuItemMap);
    }

    @Bean
    public Menu costLimitControlMenu(){
        LinkedHashMap<Integer, MenuItem> menuItemMap = new LinkedHashMap<>();
        menuItemMap.put(1, new MenuItem(IDElement.CREATE_COST_LIMIT_CMD, 1, "Создать новый лимит на затраты"));
        menuItemMap.put(2, new MenuItem(IDElement.GET_ALL_COST_LIMIT_CMD, 2, "Посмотреть все лимиты на затраты"));
        menuItemMap.put(0, new MenuItem(IDElement.APPLICATION_MENU, 0, "Назад"));

        return new Menu(IDElement.COST_LIMIT_CONTROL_MENU, menuItemMap);
    }

    @Bean
    public Menu costLimitCostMenu(){
        LinkedHashMap<Integer, MenuItem> menuItemMap = new LinkedHashMap<>();
        menuItemMap.put(1, new MenuItem(IDElement.GET_COST_LIMIT_INFO_CMD, 1, "Получить подробную информацию о лимите"));
        menuItemMap.put(2, new MenuItem(IDElement.CREATE_COST_CMD, 2, "Создать новую затрату"));
        menuItemMap.put(3, new MenuItem(IDElement.GET_ALL_COST_CMD, 3, "Посмотреть все затраты"));
        menuItemMap.put(4, new MenuItem(IDElement.GET_ALL_COST_BY_DATE_CMD, 4, "Посмотреть все затраты по дате"));
        menuItemMap.put(5, new MenuItem(IDElement.DELETE_COST_LIMIT_CMD, 5, "Удалить этот лимит на затраты"));
        menuItemMap.put(0, new MenuItem(IDElement.COST_LIMIT_CONTROL_MENU, 0, "Назад"));

        return new Menu(IDElement.COST_LIMIT_COST_MENU, menuItemMap);
    }

    @Bean
    public Menu costMenu(){
        LinkedHashMap<Integer, MenuItem> menuItemMap = new LinkedHashMap<>();
        menuItemMap.put(1, new MenuItem(IDElement.UPDATE_COST_CMD, 1, "Изменить затрату"));
        menuItemMap.put(2, new MenuItem(IDElement.DELETE_COST_CMD, 2, "Удалить затрату"));
        menuItemMap.put(0, new MenuItem(IDElement.COST_LIMIT_COST_MENU, 0, "Назад"));

        return new Menu(IDElement.COST_MENU, menuItemMap);
    }

    @Bean
    public Menu userControlMenu(){
        LinkedHashMap<Integer, MenuItem> menuItemMap = new LinkedHashMap<>();
        menuItemMap.put(1, new MenuItem(IDElement.CHANGE_USERNAME_CMD, 1, "Изменить имя пользователя"));
        menuItemMap.put(2, new MenuItem(IDElement.CHANGE_PASSWORD_CMD, 2, "Изменить пароль"));
        menuItemMap.put(3, new MenuItem(IDElement.UPDATE_USER_CMD, 3, "Изменить данные пользователя"));
        menuItemMap.put(4, new MenuItem(IDElement.DELETE_USER_CMD, 4, "Удалить пользователя"));
        menuItemMap.put(0, new MenuItem(IDElement.APPLICATION_MENU, 0, "Назад"));

        return new Menu(IDElement.USER_CONTROL_MENU, menuItemMap);
    }

    @Bean
    public ElementStorage elementStorage(List<Element> elements){
        Map<String, Element> elementMap = new HashMap<>();

        for (Element element : elements){
            elementMap.put(element.id(), element);
        }

        return new ElementStorageImpl(elementMap);
    }
}
