package ru.market.cli.interactive.element.impl;

import ru.market.cli.interactive.element.Element;
import ru.market.cli.interactive.element.ElementStorage;

import java.util.Map;

public class ElementStorageImpl implements ElementStorage {
    private Map<String, Element> elementMap;

    public ElementStorageImpl(Map<String, Element> elementMap) {
        this.elementMap = elementMap;
    }

    @Override
    public Element getElementById(String id) {
        return elementMap.get(id);
    }
}
