package com.seneda.structures.cantilever;

import static java.lang.Math.cbrt;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by seneda on 18/02/17.
 */
public class Lamination {
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

    public static double[] effectiveThicknessesStress(double[] glassThicks, double[] interlayerThicks, double omega, double hEffDefl)
    {
        double[] hEffstr = new double[glassThicks.length];
        for (int j = 0; j < hEffstr.length; j++)
            hEffstr[j] = sqrt( (pow(hEffDefl, 3)) /
                    (glassThicks[j] + 2 * omega * distanceFromMidpoint(j+1, glassThicks, interlayerThicks)) );
        return hEffstr;
    }

    public static double arraySum(double[] a)
    {
        double sum = 0;
        for (int i = 0; i < a.length; i++)
            sum += a[i];
        return sum;
    }
}
