package com.seneda.structures.util;


/**
 * Created by seneda on 21/02/17.
 */
public class Utils {
    public static double max(double[] array){
        double maximum = Double.NEGATIVE_INFINITY;
        for (double el: array){
            maximum = Math.max(maximum, el);
        }
        return maximum;
    }

    public static double min(double[] array){
        double minimum = Double.POSITIVE_INFINITY;
        for (double el: array){
            minimum = Math.min(minimum, el);
        }
        return minimum;
    }

    public static int minIndex(double[] array){
        int minIndex = 0;
        for (int i = 0; i < array.length; i++){
            minIndex = (array[i] < array[minIndex]) ? i : minIndex;
        }
        return minIndex;
    }


    public static int maxIndex(double[] array){
        int maxIndex = 0;
        for (int i = 0; i < array.length; i++){
            maxIndex = (array[i] > array[maxIndex]) ? i : maxIndex;
        }
        return maxIndex;
    }
}
