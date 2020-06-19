package ru.market.cli.printer.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.market.cli.printer.Printer;

import java.util.Collection;

public class JSONPrinter implements Printer {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> void print(T object) {
        mapToJsonAndPrint(object);
    }

    @Override
    public <T> void print(Collection<T> objects) {
        for (T object : objects){
            print(object);
        }
    }

    private void mapToJsonAndPrint(Object object){
        try {
            String json = mapper.writeValueAsString(object);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
