package ru.market.cli.interactive.element.impl;

import ru.market.cli.interactive.element.Element;
import ru.market.cli.interactive.element.IDElement;
import ru.market.cli.printer.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;

public class Menu implements Element {
    private String id;
    private LinkedHashMap<Integer, MenuItem> menuItemMap;

    public Menu(String id, LinkedHashMap<Integer, MenuItem> menuItemMap) {
        this.id = id;
        this.menuItemMap = menuItemMap;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String perform(BufferedReader reader) {
        for (MenuItem menuItem : menuItemMap.values()){
            System.out.println(menuItem.getPosition() + ". " + menuItem.getDescription());
        }

        try {
            int position;

            do {
                System.out.print("Введите номер пункта меню: ");

                String line = reader.readLine();
                if("".equals(line) || !line.matches("\\d+")){
                    position = -1;
                } else {
                    position = Integer.parseInt(line);
                }

                if(position < 0 || position >= menuItemMap.size()){
                    System.out.println("Введен некорректный пункт меню");
                }

            } while (position < 0 || position >= menuItemMap.size());

            return menuItemMap.get(position).getIdElement();
        } catch (IOException e){
            Printer.error("Ошибка выполения операции", Menu.class, e);
        }

        return IDElement.EXIT_CMD;
    }
}
