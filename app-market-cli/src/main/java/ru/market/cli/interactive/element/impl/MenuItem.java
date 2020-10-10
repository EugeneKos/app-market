package ru.market.cli.interactive.element.impl;

public class MenuItem {
    private String idElement;
    private int position;
    private String description;

    public MenuItem(String idElement, int position, String description) {
        this.idElement = idElement;
        this.position = position;
        this.description = description;
    }

    public String getIdElement() {
        return idElement;
    }

    public int getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }
}
