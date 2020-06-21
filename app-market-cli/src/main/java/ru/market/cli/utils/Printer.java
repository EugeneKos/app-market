package ru.market.cli.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;

public final class Printer {
    private static ObjectMapper mapper = new ObjectMapper();

    private Printer(){}

    public static <T> void print(T object) {
        mapToJsonAndPrint(object);
    }

    public static <T> void print(Collection<T> objects) {
        for (T object : objects){
            print(object);
        }
    }

    private static void mapToJsonAndPrint(Object object){
        try {
            String json = mapper.writeValueAsString(object);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
