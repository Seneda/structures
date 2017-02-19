package com.seneda.structures;

import com.seneda.structures.cantilever.Lamination;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by seneda on 17/02/17.
 */
public class LoadingTest {


    @org.junit.Test
    public void thicknessMinDeflection() throws Exception {

    }

    @org.junit.Test
    public void thicknessMinStress() throws Exception {

    }

    @org.junit.Test
    public void deflectionFromThickness() throws Exception {

    }

    @org.junit.Test
    public void effectiveThicknessDeflection() throws Exception {
        double[] glassThicks = {10, 8};
        double[] interlayerThicks = {1.5};
        double omega = 0.1;
        assertEquals(12.8, (Lamination.effectiveThicknessDeflection(glassThicks, omega, interlayerThicks)), 0.01);
        glassThicks = new double[] {10, 8, 6};
        interlayerThicks = new double[] {1.5, 1.5};
        omega = 0.1;
        assertEquals(15.08, (Lamination.effectiveThicknessDeflection(glassThicks, omega, interlayerThicks)), 0.01);
    }

    @Test
    public void distanceFromMidpoint() throws Exception {
        double[] glassThicks = {1,2,3};
        double[] interlayerThicks = {0.1, 0.2};
        assertEquals((1+2+3+0.1+0.2)/2-0.5, Lamination.distanceFromMidpoint(1, glassThicks, interlayerThicks),0.0001);
        assertEquals((1+2+3+0.1+0.2)/2-(1+0.1+2.0/2), Lamination.distanceFromMidpoint(2, glassThicks, interlayerThicks),0.0001);
        assertEquals(Math.abs((1+2+3+0.1+0.2)/2-(1+0.1+2+0.2+3.0/2)), Lamination.distanceFromMidpoint(3, glassThicks, interlayerThicks),0.0001);
    }


    @org.junit.Test
    public void arraySum() throws Exception {
        double[] a = {1.9, 88888, 65397, 0.8837};
        assertEquals(a[0]+a[1]+a[2]+a[3], Lamination.arraySum(a), 0.0001);
    }

}