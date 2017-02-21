package com.seneda.structures.glass;

import com.seneda.structures.cantilever.LineLoad;
import com.seneda.structures.cantilever.LoadCase;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by seneda on 19/02/17.
 */
public class LaminationTest {

    @Test
    public void getEffectiveThicknesses() throws Exception {
        Lamination l = new Lamination(new double[] {8E-3, 10E-3}, new double[]{1.5E-3}, 1.1);
        Lamination.EffectiveThicknesses e = l.getEffectiveThicknesses(2E+6, 2E6);
        assertEquals(15E-3, e.forDeflection, 0.0001);
        assertEquals(17.1E-3, e.forStress[0], 0.0001);
        assertEquals(16.1E-3, e.forStress[1], 0.0001);
    }

    @Test
    public void findSufficientLamination() throws Exception {
        LoadCase lineLoad = new LineLoad(1E3, 1.1);
        Lamination l = Lamination.findSufficientLamination(14E-3,
                                                           15E-3,
                                                           1.1,
                                                           lineLoad, lineLoad
                                                           );
        System.out.println("Glasses : "+ Arrays.toString(l.layerThicknesses));
        Lamination.EffectiveThicknesses e = l.getEffectiveThicknesses(2E6, 2E6);
        assertEquals(16.3E-3, e.forDeflection, 0.0001);
        assertEquals(18.0E-3, e.forStress[0], 0.0001);
        assertEquals(18.0E-3, e.forStress[1], 0.0001);
    }

}