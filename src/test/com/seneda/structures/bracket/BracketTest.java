package com.seneda.structures.bracket;

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
        double moment = 1E3 * length;
        double embedLength = 0.1;
        double actualTipDeflection = 20E-3;
        Bracket b = new Bracket(moment, embedLength, length, actualTipDeflection, Properties.BracketMaterials.ALUMINIUM);
        assertEquals(1.1E-2, b.getThickness(), 0.001);
    }

}