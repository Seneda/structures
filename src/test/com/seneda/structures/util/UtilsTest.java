package com.seneda.structures.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by seneda on 21/02/17.
 */
public class UtilsTest {

    double[] testArray = {12,5,29.9,1.5E1, 99};

    @Test
    public void max() throws Exception {
        assertEquals(99, Utils.max(testArray), 0.00001);
    }

    @Test
    public void min() throws Exception {
        assertEquals(5, Utils.min(testArray), 0.00001);
    }

    @Test
    public void minIndex() throws Exception {
        assertEquals(1, Utils.minIndex(testArray));
    }

    @Test
    public void maxIndex() throws Exception {
        assertEquals(4, Utils.maxIndex(testArray));
    }

}