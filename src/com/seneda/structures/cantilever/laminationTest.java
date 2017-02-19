package com.seneda.structures.cantilever;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by seneda on 19/02/17.
 */
public class laminationTest {
    @Test
    public void getEffectiveThicknesses() throws Exception {
        lamination l = new lamination(new double[] {8E-3, 10E-3}, new double[]{1.5E-3}, 1.1);
        lamination.EffectiveThicknesses e = l.getEffectiveThicknesses(2E+6);
        assertEquals(15E-3, e.forDeflection, 0.0001);
        assertEquals(17.1E-3, e.forStress[0], 0.0001);
        assertEquals(16.1E-3, e.forStress[1], 0.0001);
    }

}