package com.seneda.structures.glass;

import java.io.*;
import java.util.*;

/**
 * Created by seneda on 18/02/17.
 */
public class TableParser {
    Map<String, Double> data;

    public TableParser(String filename){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line, row;
            Scanner scanner;
            List<String> columns = new ArrayList<>();
            line = reader.readLine();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String column = stringToIdentifier(scanner.next());
                if (column.equals("SINGLECOLUMNTABLE"))
                    column = "";
                columns.add(column);
            }

            data = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                scanner = new Scanner(line);
                scanner.useDelimiter(",");
                row = stringToIdentifier(scanner.next());
                for (String column : columns) {
                    data.put(row + column, Double.parseDouble(scanner.next()));
                }
            }
        } catch (IOException e) {
            System.out.println("Something went very wrong..." + e );
        }

    }

    public double getValue(String row, String column){
        String key = row.toUpperCase()+column.toUpperCase();
        if (data.containsKey(key))
            return data.get(key);
        else
            throw new NullPointerException("No value in table for " + key + "\nData : " + data.toString());
    }

    public static String stringToIdentifier(String string){
        return string.toUpperCase().replaceAll("\\s+","");
    }

}

