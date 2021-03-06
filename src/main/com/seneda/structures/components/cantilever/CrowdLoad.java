package com.seneda.structures.components.cantilever;

import com.seneda.structures.glass.Properties;

/**
 * Created by seneda on 22/02/17.
 */
public class CrowdLoad extends LineLoad{
    public CrowdLoad(double loadMagnitude, double bendingLength) {
        super(Properties.LoadTypes.PERSONNEL, loadMagnitude, bendingLength, Properties.LoadDurations.LONG_300S);
    }
}
