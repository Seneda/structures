package com.seneda.structures.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by seneda on 21/02/17.
 *
 * Class for reading data out of csv files.
 * These will be used to store list of constants.
 * The csv files will all be formatted as such:
 *
 * .csv:
 *
 * list name, NAME
 * list source, DATA SOURCE
 * EMPTY LINE
 * Data1,Data2,Data3, ...
 */

public class ListReader {
    String listName;
    String listSource;

    Scanner scanner;

    List<Double> data;

    public ListReader(String filename, String filepath) throws FileNotFoundException{
        BufferedReader file = new BufferedReader(new FileReader(new File(filepath, filename)));
        try {
            readHeader(file);
            readData(file);
        } catch (IOException e) {
            throw new FileNotFoundException("The contents of " + filename + "are not Valid");
        }
    }

    private void readData(BufferedReader file) throws IOException{
        data = new ArrayList<Double>();
        getNextLine(file);
        while (scanner.hasNext()){
            data.add(scanner.nextDouble());
        }
    }

    private void readHeader(BufferedReader file) throws IOException{
        getNextLine(file);
        scanner.next();
        listName = scanner.next();
        getNextLine(file);
        scanner.next();
        listSource = scanner.next();
        getNextLine(file);  // To Skip the empty line
    }

    private void getNextLine(BufferedReader file) throws IOException {
        String line;
        line = file.readLine();
        line = line.replaceAll(",\\s+",",");
        scanner = new Scanner(line);
        scanner.useDelimiter(",");
    }

    public double[] toArray(){
        double[] array = new double[data.size()];
        for (int i = 0; i < data.size(); i++){
            array[i] = data.get(i);
        }
        return array;
    }

}
