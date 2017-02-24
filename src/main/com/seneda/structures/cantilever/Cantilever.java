package com.seneda.structures.cantilever;

import com.seneda.structures.glass.Glass;
import com.seneda.structures.glass.Lamination;
import com.seneda.structures.glass.Properties;

import java.util.Arrays;

import static com.seneda.structures.util.Utils.max;
import static com.seneda.structures.util.Utils.maxIndex;
import static java.lang.Math.min;


/**
 * Created by seneda on 20/02/17.
 */
public class Cantilever {
    private double height;
    private LoadCase[] loadCases;
    private Glass glass;
    private double maxAllowedDeflection;
    private double[] maxAllowedStress;
    private double[] minThickessForDeflection;
    private double[] minThicknessForStress;
    private LoadCase limitingDeflectionLoadCase;
    private LoadCase limitingStressLoadCase;
    public Lamination lamination;
    private double[] deflectionUnderLoad;
    public double limitingDeflectionUnderLoad;


    public Cantilever(double height, LoadCase[] loadCases, Glass glass){
        this.height = height;
        this.loadCases = loadCases;
        this.glass = glass;
        maxAllowedDeflection = Properties.maxDeflection;
        findMaxAllowedStresses();
        findMinimumThicknesses();
        findLamination();
        findActualDeflection();
    }

    public String toString(){
        String out = "Cantilever:\n\n";
        out += "Height: " +height + "\n";
        out += glass.toString() + "\n";
        out += String.format("Load Cases:\n\t%s\n", Arrays.toString(loadCases));
        out += String.format("Max Deflection: %4.2e\n", maxAllowedDeflection);
        out += String.format("Max Stress: %s\n", Arrays.toString(maxAllowedStress));
        out += String.format("Min Thicknesses Defl: %s\n", Arrays.toString(minThickessForDeflection));
        out += String.format("Min Thicknesses Stress: %s\n", Arrays.toString(minThicknessForStress));
        out += lamination.toString() + "\n";
        out += String.format("Limiting load for deflection:\n\t%s\n", limitingDeflectionLoadCase.toString());
        out += String.format("Limiting load for stress:\n\t%s\n", limitingStressLoadCase.toString());
        out += String.format("Actual Deflections Under Load Cases: %s\n", Arrays.toString(deflectionUnderLoad));
        out += String.format("Max Deflection Under Load: %4.2e\n", limitingDeflectionUnderLoad);
        return out;
    }

    private void findLamination() {
        lamination = Lamination.findSufficientLamination(max(minThickessForDeflection),
                                                         max(minThicknessForStress),
                                                         maxAllowedStress,
                                                         height,
                                                         loadCases
                                                         );
        if (lamination == null) {
            System.out.println("Could not find suitable lamination spec for the given requirements");
        }
    }

    private void findActualDeflection() {
        deflectionUnderLoad = new double[loadCases.length];
        for (int i = 0; i < loadCases.length; i++){
            deflectionUnderLoad[i] = loadCases[i].deflectionFromThickness(lamination.calcEffectiveThicknessForDeflection(Properties.InterlayerShearModulus.get(loadCases[i].loadDuration)));
        }
        limitingDeflectionUnderLoad = max(deflectionUnderLoad);
    }

    private void findMaxAllowedStresses() {
        maxAllowedStress = new double[loadCases.length];
        // Maximum allowed stress is the minimum of the design strengths of all the load cases
        for (int i = 0; i < loadCases.length; i++) {
            maxAllowedStress[i] = glass.designStrengthAtEdge(loadCases[i].loadType);
        }
    }

    private void findMinimumThicknesses() {
        minThickessForDeflection = new double[loadCases.length];
        for (int i = 0; i < loadCases.length; i++) {
            minThickessForDeflection[i] = loadCases[i].minimumThicknessForDeflection(maxAllowedDeflection);
        }
        limitingDeflectionLoadCase = loadCases[maxIndex(minThickessForDeflection)];
        minThicknessForStress = new double[loadCases.length];
        for (int i = 0; i < loadCases.length; i++){
            minThicknessForStress[i] = loadCases[i].minimumThicknessForStressUnderUDL(maxAllowedStress[i]);
        }
        limitingStressLoadCase = loadCases[maxIndex(minThicknessForStress)];
    }
}
