package com.seneda.structures.materials;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by seneda on 18/02/17.
 */
public class GlassTest {
    @Test
    public void designStrength() throws Exception {
        double strength = Glass.designStrength(Glass.loadTypes.WIND, Glass.structures.DRAWN, Glass.surfaceProfiles.ASPRODUCED, Glass.treatments.ANNEALED);
        assertEquals(28.125E6, strength, 0.001);
    }

    @Test
    public void designStrengthAtEdge() throws Exception {
        double strengthAtEdge = Glass.designStrengthAtEdge(Glass.edgeTypes.ASCUT, Glass.loadTypes.WIND, Glass.structures.DRAWN, Glass.surfaceProfiles.ASPRODUCED, Glass.treatments.ANNEALED);
        assertEquals(22.5E6, strengthAtEdge, 0.001);
    }

}