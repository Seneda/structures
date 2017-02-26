package com.seneda.structures.glass;

import com.seneda.structures.cantilever.LoadCase;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

import static com.seneda.structures.util.Utils.maxIndex;
import static java.lang.Math.*;

/**
 * Created by seneda on 18/02/17.
 */
public class Lamination {
    // Specified Properties
    public final double[] layerThicknesses;
    public final double[] interlayerThicknesses;
    public final double length;
    public List<Lamination> failedVersions;

    // Intermediate Properties
    private double midPoint;
    private double[] layerMidPoints;
    private double[] layerMidPointOffsets;
    private double noShearTransferThickness;
    private double momentOfInertia;

    public Map<Double, EffectiveThicknesses> effectiveThicknessesUnderLoads = new HashMap<Double, EffectiveThicknesses>();
    public double brokenThickness;
    public double brokenStress;

    public Lamination(double[] glassThicknesses, double[] interlayerThicknesses, double length){
        this.layerThicknesses = glassThicknesses;
        this.interlayerThicknesses = interlayerThicknesses;
        this.length = length;
        calcMidPoint();
        calcLayerMidpoints();
        calcLayerMidPointOffsets();
        calcNoShearTransferThicknessTerm();
        calcMomentOfInertia();
        System.out.println(toString());
    }

    public void addFailedVersions(List<Lamination> failedVersions){
        this.failedVersions = failedVersions;
    }

    public String toString(){
        return String.format("Lamination:  \n\t\tSheet Thicknesses : %s, Interlayer Thicknesses : %s", Arrays.toString(layerThicknesses), Arrays.toString(interlayerThicknesses));
    }

    public Lamination getBrokenLamination() {
        return new Lamination(ArrayUtils.remove(layerThicknesses, maxIndex(layerThicknesses)),
                              ArrayUtils.remove(interlayerThicknesses, maxIndex(interlayerThicknesses)), length);
    }

    private void calcLayerMidPointOffsets() {
        layerMidPointOffsets = new double[layerThicknesses.length];
        for (int i = 0; i < layerThicknesses.length; i++){
            layerMidPointOffsets[i] = abs(midPoint - layerMidPoints[i]);
        }
    }

    private void calcLayerMidpoints() {
        layerMidPoints = new double[layerThicknesses.length];
        double midpoint = 0;
        for (int i = 0; i < layerThicknesses.length; i++){
            layerMidPoints[i] = midpoint + layerThicknesses[i] / 2.0;
            midpoint += layerThicknesses[i];
            if (i == interlayerThicknesses.length)
                break;
            midpoint += interlayerThicknesses[i];
        }
    }

    private void calcMidPoint() {
        midPoint = (arraySum(layerThicknesses) + arraySum(interlayerThicknesses))/2.0;
    }

    private static double arraySum(double[] a) {
        double sum = 0;
        for (double el : a) sum += el;
        return sum;
    }

    private void calcNoShearTransferThicknessTerm() {
        double sumOfCubedThicknesses = 0;
        for (double glassThickness : layerThicknesses) {
            sumOfCubedThicknesses += pow(glassThickness, 3);
        }
        noShearTransferThickness = sumOfCubedThicknesses;
    }

    private void calcMomentOfInertia() {
        momentOfInertia = 0;
        for (int i = 0; i < layerThicknesses.length; i++){
            momentOfInertia += layerThicknesses[i]*pow(layerMidPointOffsets[i], 2);
        }
    }

    public static double getInterlayerShearModulus(LoadCase loadCase){
        return Properties.InterlayerShearModulus.get(loadCase.loadDuration);
    }

    public static double getInterlayerShearModulus(Properties.LoadDurations loadDuration){
        return Properties.InterlayerShearModulus.get(loadDuration);
    }

    private double calcShearFactor(double interlayerShearModulus) {
        double shearFactor = 1.0 /
                (1 + (9.6 * Properties.GlassYoungsModulus * momentOfInertia * interlayerThicknesses[0])/(interlayerShearModulus * pow(midPoint, 2) * pow(length, 2)));
        return min(0.7, shearFactor); // 0.7 is the upper limit
    }

    public double calcEffectiveThicknessForDeflection(double interlayerShearModulus) {
        return cbrt(noShearTransferThickness + 12 * calcShearFactor(interlayerShearModulus)* momentOfInertia);
    }

    private double[] calcEffectiveThicknessesForStress(double interlayerShearModulus, double effectiveThicknessForDeflection) {
        double[] effectiveThicknessesForStress = new double[layerThicknesses.length];
        double shearFactor = calcShearFactor(interlayerShearModulus);
        for (int i = 0; i < layerThicknesses.length; i++) {
            effectiveThicknessesForStress[i] =
                    sqrt((pow(effectiveThicknessForDeflection, 3))/(layerThicknesses[i] + 2 * shearFactor * layerMidPointOffsets[i]));
        }
        return effectiveThicknessesForStress;
    }

    public class EffectiveThicknesses{
        public final double forDeflection;
        public final double[] forStress;
        public final double minForStress;

        public EffectiveThicknesses(double forDeflection, double[] forStress) {
            this.forDeflection = forDeflection;
            this.forStress = forStress;
            double minForStress = Double.POSITIVE_INFINITY;
            for (double t: forStress){
                minForStress = min(minForStress, t);
            }
            this.minForStress = minForStress;
        }

    }

    public EffectiveThicknesses getEffectiveThicknesses(double interlayerShearModulus) {
        double forDeflection = calcEffectiveThicknessForDeflection(interlayerShearModulus);
        double[] forStress = calcEffectiveThicknessesForStress(interlayerShearModulus, forDeflection);
        EffectiveThicknesses e = new EffectiveThicknesses(forDeflection, forStress);
        this.effectiveThicknessesUnderLoads.put(interlayerShearModulus, e);
        return e;
    }

    public static Lamination findSufficientLamination(double minThicknessForDeflection,
                                                      double minThicknessForStress,
                                                      double[] maxStress,
                                                      double length,
                                                      LoadCase[] loadCases){
        Lamination l;
        List<Lamination> l_failed = new ArrayList<Lamination>();
        double[] glassThicknesses;
        double[] interLayerThicknesses;
        for (int noOfLayers : new int[]{2,3}){
            for (double tl: Properties.availableSheetThicknesses) {
                MAINLOOP:
                for (double ti: Properties.availableInterlayerThicknesses) {
                    if (noOfLayers == 2){
                        glassThicknesses = new double[]{tl, tl};
                        interLayerThicknesses = new double[]{ti};
                    } else {
                        glassThicknesses = new double[]{tl, tl, tl};
                        interLayerThicknesses = new double[]{ti, ti};
                    }
                    l = new Lamination(glassThicknesses, interLayerThicknesses, length);
                    for (int i = 0; i < loadCases.length; i++) {
                        EffectiveThicknesses e = l.getEffectiveThicknesses(getInterlayerShearModulus(loadCases[i]));
                        System.out.println(String.format("TeffDefl : %4.2e, TeffStr : %4.2e", e.forDeflection, e.minForStress));
                        if ((e.forDeflection < minThicknessForDeflection) || (e.minForStress < minThicknessForStress)){
                            l_failed.add(l);
                            continue MAINLOOP;
                        }
                        // If one sheet breaks it still needs to work.


                        // TODO Clean up this code for checking if the laminate would survive one sheet breaking
                        double brokenThickness;
                        if (noOfLayers == 2) {
                            brokenThickness = tl;
                        } else {
                            Lamination brokenLamination = l.getBrokenLamination();
                            brokenThickness = brokenLamination.getEffectiveThicknesses(getInterlayerShearModulus((loadCases[i]))).minForStress;
                        }

                        System.out.println(String.format("Broken Thickness :  %4.2e", brokenThickness));
                        System.out.println(String .format("max Stress: %4.2e   ---   %4.2e", maxStress[i], loadCases[i].stressFromThickness(brokenThickness)));
                        double brokenStress = loadCases[i].stressFromThickness(brokenThickness);
                        l.brokenThickness = brokenThickness;
                        l.brokenStress = brokenStress;

                        if (maxStress[i] < brokenStress) {
                            l_failed.add(l);
                            continue MAINLOOP;
                        }
                    }
                    l.failedVersions = l_failed;
                    return l;
                }
            }
        }
        return null;
    }

}
