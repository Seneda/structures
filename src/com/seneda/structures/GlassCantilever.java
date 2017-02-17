package com.seneda.structures;


import static java.lang.Math.cbrt;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by Seneda Nassir-Ali on 16/02/17.
 */
public class GlassCantilever
{

    public static double thicknessMinDeflection(double length, double line_load, double youngs_mod, double deflection)
    {
        return  (length *  cbrt( (4 * line_load) / (youngs_mod * deflection) ));
    }

    public static double thicknessMinStress(double length, double line_load, double stess)
    {
        return  sqrt( (6 * length * line_load) / (stess) );
    }


    public static double deflectionFromThickness(double line_load, double youngs_mod, double thickness)
    {
        return  ((4 * line_load * pow(line_load, 3)) / (youngs_mod * pow(thickness, 3)));
    }



    public static double effectiveThicknessDeflection(double[] glassHeights, double omega, double[] interlayerThicks)
    {
        double sumk3 = 0;

        for (int k = 0; k < glassHeights.length; k++)
        {
            sumk3 += pow(glassHeights[k], 3);
        }
        double sumhkhmk2 = 0;
        for (int k = 0; k < glassHeights.length; k++)
        {
            sumhkhmk2 +=  glassHeights[k] * pow(distanceFromMidpoint(k+1, glassHeights, interlayerThicks), 2);
        }
        return  cbrt(sumk3 + 12* omega * (sumhkhmk2));
    }

    public static double distanceFromMidpoint(int layer, double[] glassThicks, double[] interlayerThicks)
    {
        double midHeight = (arraySum(glassThicks) + arraySum(interlayerThicks)) / 2;
        return Math.abs( layerMidHeight(layer, glassThicks, interlayerThicks) - midHeight);
    }

    public static double layerMidHeight(int layer, double[] glassThicks, double[] interlayerThicks)
    {
        double midHeight = 0;
        if (layer == 1)
            midHeight = glassThicks[0] / 2;
        else {
            for (int i = 0; i < layer - 1; i++) {
                midHeight += glassThicks[i] + interlayerThicks[i];
            }
            midHeight += glassThicks[layer -1] / 2;
        }
        return midHeight;
    }



    public static double arraySum(double[] a)
    {
        double sum = 0;
        for (int i = 0; i < a.length; i++)
            sum += a[i];
        return sum;
    }

}
