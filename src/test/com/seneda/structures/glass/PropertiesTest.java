package com.seneda.structures.glass;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by seneda on 20/02/17.
 */
public class PropertiesTest {

    @Test
    public void testGlassProperties() throws Exception {
        assertEquals(70E9, Properties.GlassYoungsModulus, 0.001);
        assertEquals(45E6, Properties.CharacteristicStrengthOfBasicAnnealedGlass, 0.001);
        assertEquals(1.6, Properties.MaterialPartialFactorBasicAnnealedGlass, 0.001);
        assertEquals(1.2, Properties.MaterialPartialFactorPrestressedGlass, 0.001);
        assertEquals(1, Properties.StrengtheningFactor, 0.001);
    }

}