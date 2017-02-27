package com.seneda.structures.designs;

import com.seneda.structures.components.cantilever.*;
import com.seneda.structures.glass.Glass;
import com.seneda.structures.glass.Properties;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by seneda on 26/02/17.
 */
public class BalustradeDesignForm {
    private JComboBox<Properties.Material> comboBoxGlassMaterial;
    private JComboBox<Properties.SurfaceProfiles> comboBoxGlassSurfaceProfiles;
    private JComboBox<Properties.edgeTypes> comboBoxGlassEdgeTypes;
    private JComboBox<Properties.Treatments> comboBoxGlassTeatments;

    private JPanel JPanelBracket;
    private JComboBox<Properties.BracketMaterials> comboBoxBracketMaterial;
    private JSpinner spinnerEmbedmentDepth;

    private JSpinner spinnerHeight;
    private JLabel Edge;
    private JButton findDesignButton;
    private JTextArea textAreaDesign;
    private JPanel LoadPanel;
    private JComboBox<String> comboBoxLoadType0;
    private JComboBox comboBoxLoadDuration0;
    private JSpinner spinnerLoadMagnitude0;
    private JComboBox comboBoxLoadType1;
    private JComboBox comboBoxLoadType2;
    private JComboBox comboBoxLoadType3;
    private JComboBox comboBoxLoadDuration1;
    private JComboBox comboBoxLoadDuration2;
    private JComboBox comboBoxLoadDuration3;
    private JSpinner spinnerLoadMagnitude1;
    private JSpinner spinnerLoadMagnitude2;
    private JSpinner spinnerLoadMagnitude3;

    public BalustradeDesignForm(){
        findDesignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double height = (int) spinnerHeight.getValue() * 1.0E-3;
                    Glass glass = new Glass((Properties.Treatments) comboBoxGlassTeatments.getSelectedItem(), (Properties.SurfaceProfiles)comboBoxGlassSurfaceProfiles.getSelectedItem(),
                            (Properties.Material) comboBoxGlassMaterial.getSelectedItem(), (Properties.edgeTypes)comboBoxGlassEdgeTypes.getSelectedItem());
                    double bracketEmbedDepth = (int)spinnerEmbedmentDepth.getValue()* 1E-3;
                    Properties.BracketMaterials bracketMaterial = (Properties.BracketMaterials) comboBoxBracketMaterial.getSelectedItem();
    //                LoadCase loadCase = new LineLoad(1000, height, (Properties.LoadDurations) comboBoxLoadDuration0.getSelectedItem());
                    LoadCase[] loadCases = getLoadCases(height);

                    Balustrade b = new Balustrade(height, glass, loadCases, bracketEmbedDepth, bracketMaterial);

                    textAreaDesign.setText(b.getSummary());
                } catch (Exception ex) {
                    textAreaDesign.setText("Could not find suitable design\n"+ex+"\n"+ex.getStackTrace());
                }
            }

            private LoadCase[] getLoadCases(double height) {
                List<LoadCase> loadCases = new ArrayList<>();
                JComboBox[] loadTypes = new JComboBox[]{comboBoxLoadType0, comboBoxLoadType1, comboBoxLoadType2, comboBoxLoadType3};
                JSpinner[] loadMagnitudes = new JSpinner[]{spinnerLoadMagnitude0, spinnerLoadMagnitude1, spinnerLoadMagnitude2, spinnerLoadMagnitude3};
                JComboBox[] loadDurations = new JComboBox[]{comboBoxLoadDuration0, comboBoxLoadDuration1, comboBoxLoadDuration2, comboBoxLoadDuration3};
                for (int i = 0; i < loadTypes.length; i++){
                    String loadType = loadTypes[i].getSelectedItem().toString();
                    if (! loadType.equals("None")) {
                        LoadCase l;
                        double loadMagnitude = (int) loadMagnitudes[i].getValue();
                        Properties.LoadDurations loadDuration = (Properties.LoadDurations) loadDurations[i].getSelectedItem();
                        switch (loadType) {
                            case "Line":
                                loadCases.add(new LineLoad(loadMagnitude, height, loadDuration));
                                break;
                            case "Wind":
                                loadCases.add(new WindLoad(loadMagnitude, height));
                                break;
                            case "Personnel":
                                loadCases.add(new PersonnelLoad(loadMagnitude, height));
                                break;
                            case "Crowd":
                                loadCases.add(new CrowdLoad(loadMagnitude, height));
                                break;
                        }
                    }
                }
                return loadCases.toArray(new LoadCase[loadCases.size()]);
            }
        });
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("BalustradeDesignForm");
        frame.setContentPane(new BalustradeDesignForm().JPanelBracket);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        comboBoxGlassMaterial = new JComboBox<>(Properties.Material.values());
        comboBoxGlassSurfaceProfiles = new JComboBox<>(Properties.SurfaceProfiles.values());
        comboBoxGlassEdgeTypes = new JComboBox<>(Properties.edgeTypes.values());
        comboBoxGlassTeatments = new JComboBox<>(Properties.Treatments.values());
        comboBoxBracketMaterial = new JComboBox<>(Properties.BracketMaterials.values());
        spinnerHeight = new JSpinner(new SpinnerNumberModel(1100, 500, 2000, 10));
        SpinnerNumberModel spinnerModelMM = new SpinnerNumberModel(100, 10, 200, 5);
        spinnerEmbedmentDepth = new JSpinner(spinnerModelMM);
        comboBoxLoadType0 = new JComboBox<>(new String[]{"None", "Line", "Wind", "Personnel", "Crowd"});
        comboBoxLoadType0.setSelectedItem("Line");
        comboBoxLoadType1 = new JComboBox<>(new String[]{"None", "Line", "Wind", "Personnel", "Crowd"});
        comboBoxLoadType2 = new JComboBox<>(new String[]{"None", "Line", "Wind", "Personnel", "Crowd"});
        comboBoxLoadType3 = new JComboBox<>(new String[]{"None", "Line", "Wind", "Personnel", "Crowd"});
        comboBoxLoadDuration0 = new JComboBox<>(Properties.LoadDurations.values());
        comboBoxLoadDuration1 = new JComboBox<>(Properties.LoadDurations.values());
        comboBoxLoadDuration2 = new JComboBox<>(Properties.LoadDurations.values());
        comboBoxLoadDuration3 = new JComboBox<>(Properties.LoadDurations.values());
        SpinnerModel spinnerModelN = new SpinnerNumberModel(1000, 100, 4000, 10);
        spinnerLoadMagnitude0 = new JSpinner(spinnerModelN);
        SpinnerModel spinnerModelN1 = new SpinnerNumberModel(1000, 100, 4000, 10);
        spinnerLoadMagnitude1 = new JSpinner(spinnerModelN1);
        SpinnerModel spinnerModelN2 = new SpinnerNumberModel(1000, 100, 4000, 10);
        spinnerLoadMagnitude2 = new JSpinner(spinnerModelN2);
        SpinnerModel spinnerModelN3 = new SpinnerNumberModel(1000, 100, 4000, 10);
        spinnerLoadMagnitude3 = new JSpinner(spinnerModelN3);

    }
}
