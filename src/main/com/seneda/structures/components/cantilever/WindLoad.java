package com.seneda.structures.components.cantilever;

import com.seneda.structures.glass.Properties;

/**
 * Created by seneda on 22/02/17.
 */
public class WindLoad extends UniformLoad {
    public WindLoad(double loadMagnitude, double bendingLength){
        super(Properties.LoadTypes.WIND, loadMagnitude, bendingLength, Properties.LoadDurations.SHORT_3S);
    }
}
