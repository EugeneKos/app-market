package ru.market.cli.interactive.element.impl;

import ru.market.cli.interactive.element.Element;
import ru.market.cli.interactive.element.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class MenuImpl implements Menu {
    private String name;
    private String backName;

    private List<Element> elements;
    private Menu menu;

    public MenuImpl(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    public void setBackName(String backName) {
        this.backName = backName;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        this.menu = menu;
        perform(reader);
    }

    private void perform(BufferedReader reader){
        printElements();
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

                if(position < 0 || position > elements.size()){
                    System.out.println("Введен некорректный пункт меню");
                }

            } while (position < 0 || position > elements.size());

            if(position == 0){
                menu.back(reader);
                return;
            }

            elements.get(--position).perform(reader, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void back(BufferedReader reader){
        perform(reader);
    }

    private void printElements(){
        for (int i = 0; i < elements.size(); i++) {
            System.out.println(i + 1 + ". " + elements.get(i).name());
        }
        System.out.println("0. " + (backName == null ? "Назад" : backName));
    }
}
