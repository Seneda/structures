package com.seneda.structures.glass;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by seneda on 18/02/17.
 */
public class GlassTest {
    @Test
    public void designStrength() throws Exception {
        Glass g = new Glass(Properties.Treatments.ANNEALED,
                            Properties.SurfaceProfiles.ASPRODUCED,
                            Properties.Material.DRAWN,
                            Properties.edgeTypes.ASCUT);
        double strength = g.designStrength(Properties.LoadTypes.WIND);
        assertEquals(28.125E6, strength, 0.001);
    }

    @Test
    public void designStrengthAtEdge() throws Exception {
        Glass g = new Glass(Properties.Treatments.ANNEALED,
                            Properties.SurfaceProfiles.ASPRODUCED,
                            Properties.Material.DRAWN,
                            Properties.edgeTypes.ASCUT);

        double strengthAtEdge = g.designStrengthAtEdge(   Properties.LoadTypes.WIND);
        assertEquals(22.5E6, strengthAtEdge, 0.001);
    }

}