package com.seneda.structures;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.DoubleSummaryStatistics;

import static com.seneda.structures.GUIUtils.getNumber;
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

    public FormSelectLamination() {

        radioButton3Layers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FieldL2.setEnabled(true);
                LabelInterlayer2.setEnabled(true);
                FieldH3.setEnabled(true);
                LabelGlass3.setEnabled(true);
            }
        });

        radioButton2Layers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FieldL2.setEnabled(false);
                LabelInterlayer2.setEnabled(false);
                FieldH3.setEnabled(false);
                LabelGlass3.setEnabled(false);
            }
        });

        buttonCalcHEff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double[] glassThicks, interlayerThicks;
                System.out.println("Radiobutton2 : "+radioButton2Layers.isSelected());
                System.out.println("Radiobutton3 : "+radioButton3Layers.isSelected());
                if (radioButton2Layers.isSelected()) {
                    glassThicks = new double[2];
                    glassThicks[0] = getNumber(FieldH1);
                    glassThicks[1] = getNumber(FieldH2);
                    interlayerThicks = new double[1];
                    interlayerThicks[0] = getNumber(FieldL1);
                } else
                {
                    glassThicks = new double[3];
                    glassThicks[0] = getNumber(FieldH1);
                    glassThicks[1] = getNumber(FieldH2);
                    glassThicks[2] = getNumber(FieldH3);
                    interlayerThicks = new double[2];
                    interlayerThicks[0] = getNumber(FieldL1);
                    interlayerThicks[1] = getNumber(FieldL2);
                }
                double omega = getNumber(FieldOmega);
                double heffdefl = GlassCantilever.effectiveThicknessDeflection(glassThicks, omega, interlayerThicks);
                setNumber(FieldHEffDefl, heffdefl, 1);

            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("FormGlassCantilever");
        frame.setContentPane(new FormSelectLamination().PanelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
