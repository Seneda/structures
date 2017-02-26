package com.seneda.structures.glass;

import static com.seneda.structures.glass.Properties.*;

/**
 * Created by seneda on 18/02/17.
 */
public class Glass {

    public Treatments treatment;
    public SurfaceProfiles surfaceProfile;
    public Material material;
    public edgeTypes edgeType;

    public Glass(Treatments treatment, SurfaceProfiles surfaceProfile, Material material, edgeTypes edgeType) {
        this.treatment = treatment;
        this.surfaceProfile = surfaceProfile;
        this.material = material;
        this.edgeType = edgeType;
    }

    public String toString(){
        return String.format(
                "Glass: %s, %s, %s, %s",
                material.toString(),
                surfaceProfile.toString(),
                treatment.toString(),
                edgeType.toString());
    }

    /**
     * Implements the glass design strength equation.
     * Latex :
     * f_{g;d} = \frac{k_{mod}k_{sp}f_{g;k}}{\gamma_{M;A}} + \frac{k_v\left ( f_{b;k}-f_{g;k} \right )}{\gamma_{M;V}}
     *
     */
    public double designStrength(LoadTypes loadType){
        double Kmod = LoadDurationFactor.get(loadType);
        double Ksp = FactorForGlassSurfaceProfile.get(material, surfaceProfile);
        double Fbk = CharacteristicStrengthOfPrestressedGlass.get(material, treatment);
        double Fgk = CharacteristicStrengthOfBasicAnnealedGlass;
        double Yma = MaterialPartialFactorBasicAnnealedGlass;
        double Ymv = MaterialPartialFactorPrestressedGlass;
        double Kv = StrengtheningFactor;

        return ((Kmod*Ksp*Fgk)/Yma)+((Kv*(Fbk-Fgk))/(Ymv));
    }

    public double designStrengthAtEdge(LoadTypes loadType){
        return EdgeFactor.get(edgeType) * designStrength(loadType);
    }
}
