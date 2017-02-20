package com.seneda.structures.util;

import java.io.*;
import java.util.*;

/**
 * Created by seneda on 20/02/17.
 *
 * Class for reading data out of csv files.
 * These will be used to store constants and experimental coefficients for use in various equations.
 * The csv files will all be formatted as such:
 *
 * .csv:
 *
 * table name, NAME
 * table source, DATA SOURCE
 * table layout, [single column, double column]
 * EMPTY LINE
 *     , Col1, Col2, Col3    ( If table type is single column it will be )     , Col1
 * Row1, Data, Data, Data                                                  Row1, Data
 * Row2, Data, Data, Data                                                  Row2, Data
 *
 */
public class TableReader {
    String tableName;
    String tableSource;
    String tableLayout;

    Scanner scanner;

    List<String> columns;
    List<String> rows;

    Map<String, Double> data;


    public TableReader(String filename, String filepath) throws FileNotFoundException {
        BufferedReader file = new BufferedReader(new FileReader(new File(filepath, filename)));
        String line;
        try {
            readHeader(file);

            columns = new ArrayList<String>();
            rows = new ArrayList<String>();
            getNextLine(file);
            while (scanner.hasNext()) {
                columns.add(stringToIdentifier(scanner.next()));
            }

            data = new HashMap<String, Double>();
            while ((line = file.readLine()) != null) {
                scanner = new Scanner(line);
                scanner.useDelimiter(",");
                String row = stringToIdentifier(scanner.next());
                rows.add(row);
                for (int i = 0; i < columns.size(); i++) {
                    data.put(row+columns.get(i), Double.parseDouble(scanner.next()));
                }
            }
        } catch (IOException e) {
            throw new FileNotFoundException("The contents of " + filename + "are not Valid");
        }

    }

    private void getNextLine(BufferedReader file) throws IOException {
        String line;
        line = file.readLine();
        scanner = new Scanner(line);
        scanner.useDelimiter(",");
    }

    private void readHeader(BufferedReader file) throws IOException {
        getNextLine(file);
        scanner.next();
        tableName = scanner.next();
        getNextLine(file);
        scanner.next();
        tableSource = scanner.next();
        getNextLine(file);
        scanner.next();
        tableLayout = scanner.next();
        getNextLine(file);  // To Skip the empty line

    }

    private static String stringToIdentifier(String string){
        return string.toUpperCase().replaceAll("\\s+","");
    }

    public Double get(String row, String col){
        return data.get(stringToIdentifier(row)+stringToIdentifier(col));
    }

    public Double get(String row){
        return data.get(stringToIdentifier(row)+stringToIdentifier(columns.get(0)));
    }


}

