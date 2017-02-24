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

    public String toString(){
        return String.format("Load Case:\n\t\t\ttype: %s, force: %4.2e, duration: %s", loadType.toString(), loadMagnitude, loadDuration.toString());
    }

    public abstract double minimumThicknessForDeflection(double maxDeflection);

    public abstract double minimumThicknessForStressUnderUDL(double maxStress);

    public abstract double deflectionFromThickness(double thickness);

    public abstract double stressFromThickness(double thickness);

    public abstract double moment();
}
