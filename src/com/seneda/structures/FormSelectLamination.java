package com.seneda.structures;

import com.seneda.structures.cantilever.Loading;
import com.seneda.structures.cantilever.Lamination;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static com.seneda.structures.GUIUtils.getNumber;
import static com.seneda.structures.GUIUtils.getSmallestField;
import static com.seneda.structures.GUIUtils.setNumber;

/**
 * Created by seneda on 16/02/17.
 */
public class FormSelectLamination {
    private JTextField FieldH3;
    private JTextField FieldH2;
    private JTextField FieldH1;
    private JTextField FieldL2;
    private JTextField FieldL1;
    private JLabel LabelGlass3;
    private JLabel LabelGlass2;
    private JLabel LabelGlass1;
    private JLabel LabelInterlayer2;
    private JLabel LabelInterlayer1;
    private JTextField FieldHEffDefl;
    private JLabel labelEffectiveThickDefl;
    private JButton buttonCalcHEff;
    private JRadioButton radioButton3Layers;
    private JRadioButton radioButton2Layers;
    private JPanel PanelMain;
    private JLabel LabelOmega;
    private JTextField FieldOmega;
    private JLabel LabelHEffStr;
    private JTextField FieldHEffStr3;
    private JTextField FieldHEffStr2;
    private JTextField FieldHEffStr1;
    private JLabel LabelHEff3;
    private JLabel LabelHEffStr2;
    private JLabel LabelHEffStr1;
    private JLabel LabelMMH3;
    private JLabel LabelMML2;
    private JLabel LabelMMHeffStr3;
    private JLabel HEffDeflEqn;
    private JLabel HEffStressEqn;
    private JTextField FieldActualDeflection;
    private JLabel LabelActualDeflection;

    public FormSelectLamination() {

        radioButton3Layers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTripleFields(true);
                updateEffectiveThicknesses();
            }
        });

        radioButton2Layers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTripleFields(false);
                updateEffectiveThicknesses();
            }
        });


        FieldH3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                updateEffectiveThicknesses();
            }
        });
        FieldH2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                updateEffectiveThicknesses();
            }
        });
        FieldH1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                updateEffectiveThicknesses();
            }
        });
        FieldL2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                updateEffectiveThicknesses();
            }
        });
        FieldH1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                updateEffectiveThicknesses();
            }
        });
        FieldOmega.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                updateEffectiveThicknesses();
            }
        });
    }

    private void updateEffectiveThicknesses() {
        try {
            double[] glassThicks, interlayerThicks;
            JTextField[] hFields, lFields, heffstrFields;
            if (radioButton2Layers.isSelected()) {
                hFields = new JTextField[]{FieldH1, FieldH2};
                lFields = new JTextField[]{FieldL1};
                heffstrFields = new JTextField[]{FieldHEffStr1, FieldHEffStr2};
            } else {
                hFields = new JTextField[]{FieldH1, FieldH2, FieldH3};
                lFields = new JTextField[]{FieldL1, FieldL2};
                heffstrFields = new JTextField[]{FieldHEffStr1, FieldHEffStr2, FieldHEffStr3};
            }

            glassThicks = new double[hFields.length];
            interlayerThicks = new double[lFields.length];
            for (int i = 0; i < hFields.length; i++) {
                glassThicks[i] = getNumber(hFields[i]);
            }
            for (int i = 0; i < lFields.length; i++) {
                interlayerThicks[i] = getNumber(lFields[i]);
            }
            double omega = getNumber(FieldOmega);
            double heffdefl = Lamination.effectiveThicknessDeflection(glassThicks, omega, interlayerThicks);
            setNumber(FieldHEffDefl, heffdefl, 1);
            double[] heffstr = Lamination.effectiveThicknessesStress(glassThicks, interlayerThicks, omega, heffdefl);
            double minHEffStr = Double.POSITIVE_INFINITY;
            for (int i = 0; i < heffstr.length; i++) {
                minHEffStr = Double.min(minHEffStr, heffstr[i]);
                setNumber(heffstrFields[i], heffstr[i], 1);
                heffstrFields[i].setBackground(new Color(100,100,200));
            }

            getSmallestField(heffstrFields).setBackground(new Color(250, 100, 100));
            double actualDeflection = Loading.deflectionFromThickness(1000, 70*1000000000, heffdefl, 1);
            setNumber(FieldActualDeflection, actualDeflection, 1);
        } catch (java.lang.NumberFormatException e) {
            JTextField[] output = new JTextField[]{FieldHEffDefl, FieldHEffStr1, FieldHEffStr2, FieldHEffStr3};
            for (int i = 0; i < output.length; i++)
                output[i].setText("");
        }
    }


    public void showTripleFields(boolean show){
        FieldL2.setVisible(show);
        LabelInterlayer2.setVisible(show);
        FieldH3.setVisible(show);
        LabelGlass3.setVisible(show);
        FieldHEffStr3.setVisible(show);
        LabelHEff3.setVisible(show);
        LabelMMH3.setVisible(show);
        LabelMML2.setVisible(show);
        LabelMMHeffStr3.setVisible(show);
    }

    public JPanel getMainPanel(){
        return PanelMain;
    }


    public static void main(String[] args){
        JFrame frame = new JFrame("Choose Lamination Properties");
        frame.setContentPane(new FormSelectLamination().PanelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
