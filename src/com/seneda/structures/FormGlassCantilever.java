package com.seneda.structures;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by seneda on 16/02/17.
 */
public class FormGlassCantilever {
    private JButton ButtonCalculate;
    private JTextField FieldYoungMod;
    private JTextField FieldLength;
    private JTextField FieldLoad;
    private JTextField FieldStress;
    private JTextField FieldDelta;
    private JLabel diagram;
    private JLabel LabelThicknessDefl;
    private JLabel LabelThicknessStr;
    private JLabel LabelDelta;
    private JLabel LabelStress;
    private JLabel LabelLoad;
    private JLabel LabelLength;
    private JLabel LabelYoungMod;
    private JPanel PanelMain;
    private JTextField FieldThickStr;
    private JTextField FieldThickDefl;
    private JLabel EquationsStr;
    private JLabel EquationsDefl;

    public FormGlassCantilever() {
        ButtonCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double L, P, E, delta, sigma, thick_delf, thick_str;
                L = Double.parseDouble(FieldLength.getText());
                P = Double.parseDouble(FieldLoad.getText());
                P *= 1000; // Convert to KN
                E = Double.parseDouble(FieldYoungMod.getText());
                E *= 1000000000; // Convert to GPa/mm^2
                delta = Double.parseDouble(FieldDelta.getText());
                delta /= 1000;
                sigma = Double.parseDouble(FieldStress.getText());
                sigma *= 1000000;
                thick_delf = GlassCantilever.thicknessMinDeflection(L, P, E, delta);
                thick_str = GlassCantilever.thicknessMinStress(L, P, sigma);
                thick_delf *= 1000; // convert to mm
                thick_str *= 1000;
                FieldThickDefl.setText(String.format("%.1f", thick_delf));
                FieldThickStr.setText(String.format("%.1f",thick_str));

            }
        });
    }


    public static void main(String[] args){
        JFrame frame = new JFrame("FormGlassCantilever");
        frame.setContentPane(new FormGlassCantilever().PanelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
