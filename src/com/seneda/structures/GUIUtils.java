package com.seneda.structures;

import javax.swing.*;

/**
 * Created by seneda on 17/02/17.
 */
public class GUIUtils {
    public static double getNumber(JTextField field)
    {
        return Double.parseDouble(field.getText());
    }

    public static void setNumber(JTextField field, double value, int decimal_places)
    {
        field.setText(String.format("%."+decimal_places+"f", value));
    }
}
