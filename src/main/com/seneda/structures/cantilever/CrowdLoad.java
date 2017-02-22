package com.seneda.structures.cantilever;

import com.seneda.structures.glass.Properties;

/**
 * Created by seneda on 22/02/17.
 */
public class CrowdLoad extends UniformLoad{
    public CrowdLoad(double loaMagnitude, double bendingLength) {
        super(Properties.LoadTypes.PERSONNEL, loaMagnitude, bendingLength, Properties.LoadDurations.LONG_300S);
    }
}
