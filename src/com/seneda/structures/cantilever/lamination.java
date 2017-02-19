package com.seneda.structures.cantilever;

import com.seneda.structures.materials.Glass;

import java.util.Arrays;

import static java.lang.Math.*;

/**
 * Created by seneda on 18/02/17.
 */
public class lamination {
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

    public lamination(double[] glassThicknesses, double[] interlayerThicknesses, double length){
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

    private double calcShearFactor(double interlayerShearModulus) {
        double shearFactor = 1.0 /
                (1 + (9.6 * Glass.youngsModulus * momentOfInertia * interlayerThicknesses[0])/(interlayerShearModulus * pow(midPoint, 2) * pow(length, 2)));
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

    class EffectiveThicknesses{
        final double forDeflection;
        final double[] forStress;
        final double minForStress;

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
        return new EffectiveThicknesses(forDeflection, forStress);
    }

    private static double[] availableGlassThicknesses = new double[] {6E-3, 8E-3, 10E-3, 12E-3, 15E-3};
    private static double[] availableInterlayerThicknesses = new double[] {1.5E-3};

    public static lamination findSufficientLamination(double minThicknessForDeflection, double minThicknessForStress, double length, double interlayerShearModulus){
        lamination l;
        double[] glassThicknesses;
        double[] interLayerThicknesses;
        for (int noOfLayers : new int[]{2,3}){
            for (double tl: availableGlassThicknesses) {
                for (double ti: availableInterlayerThicknesses) {
                    if (noOfLayers == 2){
                        glassThicknesses = new double[]{tl, tl};
                        interLayerThicknesses = new double[]{ti};
                    } else {
                        glassThicknesses = new double[]{tl, tl, tl};
                        interLayerThicknesses = new double[]{ti, ti};
                    }
                    l = new lamination(glassThicknesses, interLayerThicknesses, length);
                    EffectiveThicknesses e = l.getEffectiveThicknesses(interlayerShearModulus);
                    if ((e.forDeflection > minThicknessForDeflection) && (e.minForStress > minThicknessForStress))
                        return l;
                }
            }
        }
        return null;
    }

}
