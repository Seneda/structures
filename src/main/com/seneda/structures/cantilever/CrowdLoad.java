package com.seneda.structures.cantilever;

import com.seneda.structures.glass.Properties;

/**
 * Created by seneda on 22/02/17.
 */
public class CrowdLoad extends UniformLoad{
    public CrowdLoad(double loadMagnitude, double bendingLength) {
        super(Properties.LoadTypes.PERSONNEL, loadMagnitude, bendingLength, Properties.LoadDurations.LONG_300S);
    }
}
