package com.seneda.structures.cantilever;

import com.seneda.structures.glass.Properties;

/**
 * Created by seneda on 20/02/17.
 */
public abstract class LoadCase {

    public final Properties.LoadTypes loadType;
    public final double loadMagnitude;
    public final double bendingLength;
    public final Properties.LoadDurations loadDuration;


    public LoadCase(Properties.LoadTypes loadType, Properties.LoadDurations loadDuration,
                    double loadMagnitude, double bendingLength) {
        this.loadType = loadType;
        this.loadMagnitude = loadMagnitude;
        this.bendingLength = bendingLength;
        this.loadDuration = loadDuration;
    }

    public abstract double minimumThicknessForDeflection(double maxDeflection);

    public abstract double minimumThicknessForStress(double maxStress);

    public abstract double deflectionFromThickness(double thickness);
}
