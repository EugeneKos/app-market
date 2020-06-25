package ru.market.cli.printer;

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
}
