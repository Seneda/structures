package com.seneda.structures.cantilever;

import com.seneda.structures.glass.Properties;

import static java.lang.Math.cbrt;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by seneda on 21/02/17.
 */
public class UniformLoad extends LoadCase {

    public UniformLoad(Properties.LoadTypes loadType, double loadMagnitude, double bendingLength, Properties.LoadDurations loadDuration) {
        super(loadType, loadDuration, loadMagnitude, bendingLength);
    }

    public double minimumThicknessForDeflection(double maxDeflection){
        return cbrt((12*loadMagnitude*pow(bendingLength, 4))/
                (8*Properties.YoungsModulus*maxDeflection));
    }

    public double minimumThicknessForStressUnderUDL(double maxStress) {
        return bendingLength*sqrt((3* Properties.ULSFactor*loadMagnitude/maxStress));
    }

    public double deflectionFromThickness(double thickness){
        return (12*loadMagnitude*pow(bendingLength, 4)/(8*Properties.YoungsModulus*pow(thickness, 3)));
    }

    public double stressFromThickness(double thickness)
    {
        return (3 * loadMagnitude * Properties.BrokenSheetFactor * pow(bendingLength, 2)) / (pow(thickness, 2));
    }
}
