package ru.market.cli.printer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public interface Printer {
    void printTable(LinkedHashMap<String, List<String>> table);
    void printRow(LinkedHashMap<String, String> row);

    static List<List<String>> createEmptyColumns(int size){
        List<List<String>> columns = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            columns.add(new ArrayList<>());
        }
        return columns;
    }

    static void error(String message, Class<?> clazz){
        System.out.println("ERROR: " + message);
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error(message);
    }

    static void error(String message, Class<?> clazz, Exception e){
        System.out.println("ERROR: " + message);
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error(message, e);
    }
}
