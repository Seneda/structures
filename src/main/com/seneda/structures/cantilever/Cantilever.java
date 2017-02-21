package com.seneda.structures.cantilever;

import com.seneda.structures.glass.Glass;
import com.seneda.structures.glass.Lamination;

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
    Lamination lamination;
    private double maxAllowedDeflection;
    private double maxAllowedStress;
    private double[] minThickessForDeflection;
    private double[] minThicknessForStress;
    private double[] actualDeflectionUnderLoad;
    private LoadCase limitingDeflectionLoadCase;
    private LoadCase limitingStressLoadCase;

    public Cantilever(double height, LoadCase[] loadCases, Glass glass){
        this.height = height;
        this.loadCases = loadCases;
        this.glass = glass;
        maxAllowedDeflection = 25E-6; //TODO Find out where this value comes from
        findMaxAllowedDeflection();
        findMinimumThicknesses();
        findLamination();
        findActualDeflection();
    }


    private void findLamination() {
        lamination = Lamination.findSufficientLamination(max(minThickessForDeflection),
                                                         max(minThicknessForStress),
                                                         height,
                                                         limitingDeflectionLoadCase,
                                                         limitingStressLoadCase
                                                         );
    }

    private void findActualDeflection() {
//        actualDeflectionUnderLoad = new double[loadCases.length];
//        for (int i = 0; i < loadCases.length; i++){
//            actualDeflectionUnderLoad[i] = loadCases[i].deflectionFromThickness(lamination.getEffectiveThicknesses().forDeflection);
//        }
        // TODO Figure out how to refactor to make this bit work.
    }

    private void findMaxAllowedDeflection() {
        maxAllowedStress = Double.POSITIVE_INFINITY;
        // Maximum allowed stress is the minimum of the design strengths of all the load cases
        for (LoadCase loadCase: loadCases) {
            maxAllowedStress = min(maxAllowedStress, glass.designStrengthAtEdge(loadCase.loadType));
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
            minThicknessForStress[i] = loadCases[i].minimumThicknessForStress(maxAllowedStress);
        }
        limitingStressLoadCase = loadCases[maxIndex(minThicknessForStress)];
    }
}
