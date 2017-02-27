package com.seneda.structures.components.bracket;

import com.seneda.structures.components.cantilever.LineLoad;
import com.seneda.structures.components.cantilever.LoadCase;
import com.seneda.structures.glass.Properties;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by seneda on 23/02/17.
 */
public class BracketTest {
    @Test
    public void getThickness() throws Exception {
        double length = 1.1;
        LoadCase[] load = {new LineLoad(1e3, length, Properties.LoadDurations.MID_30S)};
        double embedLength = 0.1;
        double actualTipDeflection = 20E-3;
        Bracket b = new Bracket(load, embedLength, length, actualTipDeflection, Properties.BracketMaterials.ALUMINIUM);
        assertEquals(12E-3, b.getThickness(), 0.001);
    }

}