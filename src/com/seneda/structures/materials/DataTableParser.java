package com.seneda.structures.materials;

import java.io.*;
import java.util.*;

/**
 * Created by seneda on 18/02/17.
 */
public class DataTableParser {
    Map<String, Double> data;

    public DataTableParser(String filename){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line, row;
            Scanner scanner;
            List<String> columns = new ArrayList<String>();
            line = reader.readLine();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String column = stringToIdentifier(scanner.next());
                if (column.equals("SINGLECOLUMNTABLE"))
                    column = "";
                columns.add(column);
            }

            data = new HashMap<String, Double>();

            while ((line = reader.readLine()) != null) {
                scanner = new Scanner(line);
                scanner.useDelimiter(",");
                row = stringToIdentifier(scanner.next());
                for (int i = 0; i < columns.size(); i++) {
                    data.put(row + columns.get(i), Double.parseDouble(scanner.next()));
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

    public static void main(String[] args) throws IOException{
        System.out.println(new File(".").getCanonicalPath());

        DataTableParser d = new DataTableParser(new File(".").getCanonicalPath() + "/src/com/seneda/structures/materials/glass_data/factor_for_glass_surface_profile.csv");
        System.out.println(" data "+d.data.toString());

        System.out.println("The value of drawn, as porduced is : " + d.getValue(Glass.structures.DRAWN.toString(), Glass.surfaceProfiles.ASPRODUCED.toString()));
    }

}

