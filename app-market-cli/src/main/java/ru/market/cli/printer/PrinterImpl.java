package ru.market.cli.printer;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PrinterImpl implements Printer {
    @Override
    public void printTable(LinkedHashMap<String, List<String>> table) {
        Collection<List<String>> columns = table.values();
        Set<String> tableHeader = table.keySet();

        Collection<List<String>> convertedTable = convertTableFromMap(table);
        List<Integer> maxLengthColumns = getMaxLengthColumns(convertedTable);

        int delimiterLength = getDelimiterLength(maxLengthColumns);
        String formatForTablePrint = createFormatForTablePrint(maxLengthColumns);

        String delimiter = getDelimiter(delimiterLength);

        System.out.println(delimiter);

        printTableRow(formatForTablePrint, tableHeader.toArray());

        System.out.println(delimiter);

        List<String> firstColumn = columns.iterator().next();
        for (int i = 0; i < firstColumn.size(); i++) {
            List<String> tableRow = new ArrayList<>();
            for (List<String> column : columns){
                tableRow.add(column.get(i));
            }
            printTableRow(formatForTablePrint, tableRow.toArray());
        }

        System.out.println(delimiter);
    }

    @Override
    public void printRow(LinkedHashMap<String, String> row) {
        if(row == null || row.isEmpty()){
            return;
        }

        StringBuilder builder = new StringBuilder("[ ");

        for (Map.Entry<String, String> entry : row.entrySet()){
            builder.append(String.format("%s: %s | ", entry.getKey(), entry.getValue()));
        }

        String toBePrint = builder.substring(0, builder.length() - 2);
        toBePrint = toBePrint.concat("]");

        String delimiter = getDelimiter(toBePrint.length());

        System.out.println(delimiter);
        System.out.println(toBePrint);
        System.out.println(delimiter);
    }

    private String getDelimiter(int delimiterLength){
        StringBuilder builder = new StringBuilder(delimiterLength);
        for (int i = 0; i < delimiterLength; i++) {
            builder.append("-");
        }
        return builder.toString();
    }

    private Collection<List<String>> convertTableFromMap(LinkedHashMap<String, List<String>> table){
        List<List<String>> convertedTable = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : table.entrySet()){
            String columnName = entry.getKey();
            List<String> column = entry.getValue();
            conversionInUTF8(column);

            List<String> columnWithColumnName = new ArrayList<>(column);
            columnWithColumnName.add(columnName);

            convertedTable.add(columnWithColumnName);
        }

        return convertedTable;
    }

    private List<Integer> getMaxLengthColumns(Collection<List<String>> columns){
        List<Integer> columnSizes = new ArrayList<>();

        for (List<String> column : columns){
            int maxLength = 0;
            for (String row : column){
                if(row.length() > maxLength){
                    maxLength = row.length();
                }
            }
            columnSizes.add(maxLength);
        }

        return columnSizes;
    }

    private int getDelimiterLength(List<Integer> maxLengthColumns){
        int delimiterLength = 0;

        for (Integer maxLengthColumn : maxLengthColumns){
            delimiterLength = delimiterLength + maxLengthColumn + 3;
        }

        return delimiterLength + 1;
    }

    private String createFormatForTablePrint(List<Integer> maxLengthColumns){
        StringBuilder builder = new StringBuilder();
        for (Integer maxLengthColumn : maxLengthColumns){
            builder.append("| %-").append(maxLengthColumn).append("s ");
        }
        builder.append("|");
        return builder.toString();
    }

    private void printTableRow(String format, Object... args){
        System.out.println(String.format(format, args));
    }

    private void conversionInUTF8(List<String> column){
        for (int i = 0; i < column.size(); i++) {
            column.set(i, new String(column.get(i).getBytes(), StandardCharsets.UTF_8));
        }
    }
}
