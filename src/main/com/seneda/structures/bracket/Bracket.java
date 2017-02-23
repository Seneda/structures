package com.seneda.structures.bracket;

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
    private final double moment;
    private double thickness;
    private double thicknessForDeflection;
    private double thicknessForStress;
    private final double actualTipDeflection;
    private double youngsModulus;
    private double yieldStress;

    public Bracket(double moment, double embedmentDepth, double glassLength, double actualTipDeflection,
                   Properties.BracketMaterials material){
        this.moment = moment;
        this.embedmentDepth = embedmentDepth;
        this.glassLength = glassLength;
        this.actualTipDeflection = actualTipDeflection;
        this.youngsModulus = Properties.BracketMaterialProperties.get(material, Properties.BracketMaterialPropertyTypes.YOUNGSMODULUS);
        this.yieldStress = Properties.BracketMaterialProperties.get(material, Properties.BracketMaterialPropertyTypes.YIELDSTRESS);

        calcThicknessForStress();
        calcThicknessForDeflection();
        calcThickness();
    }

    public double getThickness(){
        return thickness;
    }

    private void calcThicknessForDeflection() {
        thicknessForDeflection = cbrt((4*moment*embedmentDepth*(glassLength + embedmentDepth))/
                                      (youngsModulus * (Properties.maxDeflection - actualTipDeflection)));
    }

    private void calcThicknessForStress() {
        thicknessForStress = sqrt((6*moment*Properties.ULSFactor)/(yieldStress));
    }

    private void calcThickness() {
        thickness = max(thicknessForDeflection, thicknessForStress);
    }


}
