package com.seneda.structures.components.bracket;

import com.seneda.structures.components.cantilever.LoadCase;
import com.seneda.structures.glass.Properties;

import static java.lang.Math.cbrt;
import static java.lang.Math.max;
import static java.lang.Math.sqrt;

/**
 * Created by seneda on 23/02/17.
 */
public class Bracket {
    private final double glassLength;
    public final double embedmentDepth;
    public final Properties.BracketMaterials material;
    public double[] moments;
    public double maxMoment;
    public LoadCase[] loadCases;
    private double thickness;
    public double thicknessForDeflection;
    public double thicknessForStress;
    private final double actualTipDeflection;
    private double youngsModulus;
    private double yieldStress;

    public Bracket(LoadCase[] loadCases, double embedmentDepth, double glassLength, double actualTipDeflection,
                   Properties.BracketMaterials material){
        this.loadCases = loadCases;
        this.embedmentDepth = embedmentDepth;
        this.glassLength = glassLength;
        this.actualTipDeflection = actualTipDeflection;
        this.material = material;
        this.youngsModulus = Properties.BracketMaterialProperties.get(material, Properties.BracketMaterialPropertyTypes.YOUNGSMODULUS);
        this.yieldStress = Properties.BracketMaterialProperties.get(material, Properties.BracketMaterialPropertyTypes.YIELDSTRESS);

        calcMaxMoment();
        calcThicknessForStress();
        calcThicknessForDeflection();
        calcThickness();
    }

    public String toString(){
        String out = "";
        out += String.format("Bracket : \n\t\tEmbed Depth : %4.2e  Thickness required : %4.2e\n", embedmentDepth, getThickness());
        out += "\n\t Moments : ";
        for (int i = 0; i < moments.length; i++){
            out += String.format("\n\t\t Load %d : %4.2e", i, moments[i]);
        }
        out += String.format("\n\t T for Defl : %4.2e", thicknessForDeflection);
        out += String.format("\n\t T for Str  : %4.2e", thicknessForStress);
        return out;
    }

    private void calcMaxMoment() {
        maxMoment = 0;
        moments = new double[loadCases.length];
        for (int i = 0; i < loadCases.length; i++){
            moments[i] = loadCases[i].moment();
            maxMoment = max(maxMoment, moments[i]);
        }
    }

    public double getThickness(){
        for (double availableThickness: Properties.availableBracketThicknesses) {
            if (availableThickness > thickness) {
                return availableThickness;
            }
        }
        return 0;
    }

    private void calcThicknessForDeflection() {
        thicknessForDeflection = cbrt((4* maxMoment *embedmentDepth*(glassLength + embedmentDepth))/
                                      (youngsModulus * (Properties.maxDeflection - actualTipDeflection)));
    }

    private void calcThicknessForStress() {
        thicknessForStress = sqrt((6* maxMoment *Properties.ULSFactor)/(yieldStress));
    }

    private void calcThickness() {
        thickness = max(thicknessForDeflection, thicknessForStress);
    }


}
