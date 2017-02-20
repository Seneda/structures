package com.seneda.structures.materials;

import com.seneda.structures.materials.glass_data.GlassProperties;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by seneda on 18/02/17.
 */
public class GlassTest {
    @Test
    public void designStrength() throws Exception {
        Glass g = new Glass(GlassProperties.Treatments.ANNEALED,
                            GlassProperties.SurfaceProfiles.ASPRODUCED,
                            GlassProperties.Material.DRAWN,
                            GlassProperties.edgeTypes.ASCUT);
        double strength = g.designStrength(GlassProperties.LoadTypes.WIND);
        assertEquals(28.125E6, strength, 0.001);
    }

    @Test
    public void designStrengthAtEdge() throws Exception {
        Glass g = new Glass(GlassProperties.Treatments.ANNEALED,
                            GlassProperties.SurfaceProfiles.ASPRODUCED,
                            GlassProperties.Material.DRAWN,
                            GlassProperties.edgeTypes.ASCUT);

        double strengthAtEdge = g.designStrengthAtEdge(   GlassProperties.LoadTypes.WIND);
        assertEquals(22.5E6, strengthAtEdge, 0.001);
    }

}