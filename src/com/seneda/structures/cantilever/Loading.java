package com.seneda.structures.cantilever;


import static java.lang.Math.cbrt;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by Seneda Nassir-Ali on 16/02/17.
 */
public class Loading
{

    public static double thicknessMinDeflection(double length, double line_load, double youngs_mod, double deflection)
    {
        return  (length *  cbrt( (4 * line_load) / (youngs_mod * deflection) ));
    }

    public static double thicknessMinStress(double length, double line_load, double stess)
    {
        return  sqrt( (6 * length * line_load) / (stess) );
    }


    public static double deflectionFromThickness(double line_load, double youngs_mod, double thickness, double length)
    {
        return  ((4 * line_load * pow(length, 3)) / (youngs_mod * pow(thickness, 3)));
    }


}
