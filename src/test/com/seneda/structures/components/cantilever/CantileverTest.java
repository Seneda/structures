package com.seneda.structures.components.cantilever;

import com.seneda.structures.glass.Glass;
import com.seneda.structures.glass.Properties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by seneda on 21/02/17.
 */
@RunWith(Parameterized.class)
public class CantileverTest {
    private double height;
    private LoadCase[] loadCases;
    private Glass glass;

    public CantileverTest(double height,  Glass glass, LoadCase[] loadCases){
        this.height = height;
        this.loadCases = loadCases;
        this.glass = glass;
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        double height = 1.1;
        Glass basicGlass = new Glass(
                Properties.Treatments.ANNEALED,
                Properties.SurfaceProfiles.ASPRODUCED,
                Properties.Material.FLOAT,
                Properties.edgeTypes.ASCUT);
        Glass strongGlass = new Glass(
                Properties.Treatments.THERMALLYTOUGHENED,
                Properties.SurfaceProfiles.ASPRODUCED,
                Properties.Material.DRAWN,
                Properties.edgeTypes.POLISHED
        );
        LoadCase wind = new WindLoad(1E3, height);
        LoadCase crowd = new CrowdLoad(2E3, height);
        LoadCase line = new LineLoad(1.5E3, height, Properties.LoadDurations.LONG_300S);
        Object[][] testCases = new Object[][] {
                {height, basicGlass, new LoadCase[] {wind}},
                {height, basicGlass, new LoadCase[] {crowd}},
                {height, basicGlass, new LoadCase[] {line}},
                {height, strongGlass, new LoadCase[] {wind}},
                {height, strongGlass, new LoadCase[] {crowd}},
                {height, strongGlass, new LoadCase[] {line}},
                {height, strongGlass, new LoadCase[] {line, wind}},
                {height, strongGlass, new LoadCase[] {line}},
                {height, strongGlass, new LoadCase[] {wind, crowd}}};
        return Arrays.asList(testCases);
    }

    @Test
    public void testCantilever() throws Exception {
        System.out.println("\n\n*****************************************\n" +
                "*****************************************\n\n");
        System.out.println("Attempted laminations: ");
        Cantilever cantilever = new Cantilever(height, loadCases, glass);
        System.out.println("\n");

        System.out.println(cantilever);
        assertTrue("Actual Deflection is ("+cantilever.limitingDeflectionUnderLoad+") should be lower than "+25E-3, cantilever.limitingDeflectionUnderLoad < 25E-3);
    }

}