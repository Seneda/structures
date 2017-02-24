package com.seneda.structures.cantilever;


import com.seneda.structures.glass.Properties;

import static com.seneda.structures.glass.Properties.ULSFactor;
import static java.lang.Math.cbrt;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by Seneda Nassir-Ali on 16/02/17.
 */
public class LineLoad extends LoadCase
{
    public LineLoad(double loadMagnitude, double bendingLength, Properties.LoadDurations loadDuration){
        super(Properties.LoadTypes.LINE, loadDuration, loadMagnitude, bendingLength);
    }

    public double minimumThicknessForDeflection(double maxDeflection)
    {
        return  (bendingLength *  cbrt( (4 * loadMagnitude) /
                (Properties.GlassYoungsModulus * maxDeflection)));
    }

    public double minimumThicknessForStressUnderUDL(double maxstress)
    {
        return  sqrt( (6 * bendingLength * ULSFactor * loadMagnitude) /
                (maxstress) );
    }

    public double deflectionFromThickness(double thickness)
    {
        return  ((4 * loadMagnitude * pow(bendingLength, 3)) / (Properties.GlassYoungsModulus * pow(thickness, 3)));
    }

    public double stressFromThickness(double thickness)
    {
        return (6 * loadMagnitude * bendingLength * Properties.BrokenSheetFactor) / (pow(thickness, 2));
    }

    public double moment()
    {
        return loadMagnitude * bendingLength;
    }
}
