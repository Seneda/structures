package com.seneda.structures.materials.glass_data;

import com.seneda.structures.materials.Glass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by seneda on 20/02/17.
 */
public class GlassPropertiesTest {

    @Test
    public void testGlassProperties() throws Exception {
        assertEquals(70E9, GlassProperties.YoungsModulus, 0.001);
        assertEquals(45E6, GlassProperties.CharacteristicStrengthOfBasicAnnealedGlass, 0.001);
        assertEquals(1.6, GlassProperties.MaterialPartialFactorBasicAnnealedGlass, 0.001);
        assertEquals(1.2, GlassProperties.MaterialPartialFactorPrestressedGlass, 0.001);
        assertEquals(1, GlassProperties.StrengtheningFactor, 0.001);
    }

}