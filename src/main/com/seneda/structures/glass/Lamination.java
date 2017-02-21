package com.seneda.structures.glass;

import com.seneda.structures.cantilever.LoadCase;

import java.util.Arrays;

import static java.lang.Math.*;

/**
 * Created by seneda on 18/02/17.
 */
public class Lamination {
    // Specified Properties
    public final double[] layerThicknesses;
    public final double[] interlayerThicknesses;
    public final double length;

    // Intermediate Properties
    private double midPoint;
    private double[] layerMidPoints;
    private double[] layerMidPointOffsets;
    private double noShearTransferThickness;
    private double momentOfInertia;


    public Lamination(double[] glassThicknesses, double[] interlayerThicknesses, double length){
        this.layerThicknesses = glassThicknesses;
        this.interlayerThicknesses = interlayerThicknesses;
        this.length = length;
        calcMidPoint();
        calcLayerMidpoints();
        calcLayerMidPointOffsets();
        calcNoShearTransferThicknessTerm();
        calcMomentOfInertia();
        System.out.println(Arrays.toString(layerMidPoints));
        System.out.println(Arrays.toString(layerMidPointOffsets));
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
        System.out.println("no Sheear Thickness : "+ noShearTransferThickness);

    }

    private void calcMomentOfInertia() {
        momentOfInertia = 0;
        for (int i = 0; i < layerThicknesses.length; i++){
            momentOfInertia += layerThicknesses[i]*pow(layerMidPointOffsets[i], 2);
        }
        System.out.println("Second moment of area : "+ momentOfInertia);

    }

    private static double getInterlayerShearModulus(LoadCase loadCase){
        return Properties.InterlayerShearModulus.get(loadCase.loadDuration);
    }

    private double calcShearFactor(double interlayerShearModulus) {
        double shearFactor = 1.0 /
                (1 + (9.6 * Properties.YoungsModulus * momentOfInertia * interlayerThicknesses[0])/(interlayerShearModulus * pow(midPoint, 2) * pow(length, 2)));
        return min(0.7, shearFactor); // 0.7 is the upper limit
    }

    private double calcEffectiveThicknessForDeflection(double interlayerShearModulus) {
        return cbrt(noShearTransferThickness + 12 * calcShearFactor(interlayerShearModulus)* momentOfInertia);
    }

    private double[] calcEffectiveThicknessesForStress(double interlayerShearModulus, double effectiveThicknessForDeflection) {
        double[] effectiveThicknessesForStress = new double[layerThicknesses.length];
        double shearFactor = calcShearFactor(interlayerShearModulus);
        System.out.println("Shear Factor : "+ shearFactor);
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

    public EffectiveThicknesses getEffectiveThicknesses(double deflectionInterlayerShearModulus, double stressInterlayerShearModulus) {
        double forDeflection = calcEffectiveThicknessForDeflection(deflectionInterlayerShearModulus);
        double[] forStress = calcEffectiveThicknessesForStress(stressInterlayerShearModulus, forDeflection);
        return new EffectiveThicknesses(forDeflection, forStress);
    }

    public static Lamination findSufficientLamination(double minThicknessForDeflection,
                                                      double minThicknessForStress,
                                                      double length,
                                                      LoadCase deflectionLoadCase,
                                                      LoadCase stressLoadCase){
        Lamination l;
        double deflectionInterlayerShearModulus = getInterlayerShearModulus(deflectionLoadCase);
        double stressInterlayerShearModulus = getInterlayerShearModulus(stressLoadCase);
        double[] glassThicknesses;
        double[] interLayerThicknesses;
        for (int noOfLayers : new int[]{2,3}){
            for (double tl: Properties.availableSheetThicknesses) {
                for (double ti: Properties.availableInterlayerThicknesses) {
                    if (noOfLayers == 2){
                        glassThicknesses = new double[]{tl, tl};
                        interLayerThicknesses = new double[]{ti};
                    } else {
                        glassThicknesses = new double[]{tl, tl, tl};
                        interLayerThicknesses = new double[]{ti, ti};
                    }
                    l = new Lamination(glassThicknesses, interLayerThicknesses, length);
                    EffectiveThicknesses e = l.getEffectiveThicknesses(deflectionInterlayerShearModulus, stressInterlayerShearModulus);
                    if ((e.forDeflection > minThicknessForDeflection) && (e.minForStress > minThicknessForStress))
                        return l;
                }
            }
        }
        return null;
    }

}
