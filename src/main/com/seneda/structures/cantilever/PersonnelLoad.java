package com.seneda.structures.cantilever;

import com.seneda.structures.glass.Properties;

/**
 * Created by seneda on 22/02/17.
 */
public class PersonnelLoad extends UniformLoad {
    public PersonnelLoad(double loadMagnitude, double bendingLength) {
        super(Properties.LoadTypes.PERSONNEL, loadMagnitude, bendingLength, Properties.LoadDurations.MID_30S);
    }
}
