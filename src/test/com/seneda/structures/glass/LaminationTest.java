package com.seneda.structures.glass;

import com.seneda.structures.components.cantilever.LineLoad;
import com.seneda.structures.components.cantilever.LoadCase;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by seneda on 19/02/17.
 */
public class LaminationTest {

    @Test
    public void getEffectiveThicknesses() throws Exception {
        Lamination l = new Lamination(new double[] {8E-3, 10E-3}, new double[]{1.5E-3}, 1.1);
        Lamination.EffectiveThicknesses e = l.getEffectiveThicknesses(2E6);
        assertEquals(15E-3, e.forDeflection, 0.0001);
        assertEquals(17.1E-3, e.forStress[0], 0.0001);
        assertEquals(16.1E-3, e.forStress[1], 0.0001);
    }

    @Test
    public void findSufficientLamination() throws Exception {
        LoadCase lineLoad = new LineLoad(1E3, 1.1, Properties.LoadDurations.SHORT_3S);
        Lamination l = Lamination.findSufficientLamination(14E-3,
                                                           15E-3,
                                                           new double[] {1e+08, 1e+08},
                                                           1.1,
                                                           new LoadCase[]{lineLoad, lineLoad}
                                                           );
        Lamination.EffectiveThicknesses e = l.getEffectiveThicknesses(2E6);
        assertEquals(16.3E-3, e.forDeflection, 0.0001);
        assertEquals(18.0E-3, e.forStress[0], 0.0001);
        assertEquals(18.0E-3, e.forStress[1], 0.0001);
    }

}