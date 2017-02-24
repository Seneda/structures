package com.seneda.structures.bracket;

import com.seneda.structures.cantilever.LoadCase;
import com.seneda.structures.glass.Properties;

import static java.lang.Math.cbrt;
import static java.lang.Math.max;
import static java.lang.Math.sqrt;

/**
 * Created by seneda on 23/02/17.
 */
public class Bracket {
    private final double glassLength;
    private final double embedmentDepth;
    private double maxMoment;
    public LoadCase[] loadCases;
    private double thickness;
    private double thicknessForDeflection;
    private double thicknessForStress;
    private final double actualTipDeflection;
    private double youngsModulus;
    private double yieldStress;

    public Bracket(LoadCase[] loadCases, double embedmentDepth, double glassLength, double actualTipDeflection,
                   Properties.BracketMaterials material){
        this.loadCases = loadCases;
        this.embedmentDepth = embedmentDepth;
        this.glassLength = glassLength;
        this.actualTipDeflection = actualTipDeflection;
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
        return out;
    }

    private void calcMaxMoment() {
        maxMoment = 0;
        for (LoadCase loadCase: loadCases){
            maxMoment = max(maxMoment, loadCase.moment());
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
