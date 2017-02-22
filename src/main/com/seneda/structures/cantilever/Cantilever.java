package com.seneda.structures.cantilever;

import com.seneda.structures.glass.Glass;
import com.seneda.structures.glass.Lamination;
import com.seneda.structures.glass.Properties;
import org.apache.commons.lang3.builder.MultilineRecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

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
    private double[] deflectionUnderLoad;
    public double limitingDeflectionUnderLoad;
    private LoadCase limitingDeflectionLoadCase;
    private LoadCase limitingStressLoadCase;


    public Cantilever(double height, LoadCase[] loadCases, Glass glass){
        this.height = height;
        this.loadCases = loadCases;
        this.glass = glass;
        maxAllowedDeflection = Properties.maxDeflection;
        findMaxAllowedDeflection();
        findMinimumThicknesses();
        findLamination();
        findActualDeflection();
    }

    public String toString(){
        return ReflectionToStringBuilder.toString(this, new MultilineRecursiveToStringStyle());
    }

    private void findLamination() {
        lamination = Lamination.findSufficientLamination(max(minThickessForDeflection),
                                                         max(minThicknessForStress),
                                                         height,
                                                         limitingDeflectionLoadCase,
                                                         limitingStressLoadCase
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
