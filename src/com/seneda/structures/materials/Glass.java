package com.seneda.structures.materials;


/**
 * Created by seneda on 18/02/17.
 */
public class Glass {
    static double Fgk = 45E+6;  // CHARACTERISTIC STRENGTH OF BASIC ANNEALED GLASS, Always 45N/mm2
    static double Yma = 1.6;    // MATERIAL PARTIAL FACTOR FOR BASIC ANNEALED GLASS, Always 1.6
    static double Ymv = 1.2;    // MATERIAL PARTIAL FACTOR FOR PRESTRESSED GLASS, Always 1.2
    static double Kv  = 1;      // STRENGTHENING FACTOR, Always 1
    public static double youngsModulus = 70E+9;

    /**
     * Implements the glass design strength equation.
     * Latex :
     * f_{g;d} = \frac{k_{mod}k_{sp}f_{g;k}}{\gamma_{M;A}} + \frac{k_v\left ( f_{b;k}-f_{g;k} \right )}{\gamma_{M;V}}
     *
     */
    public static double designStrength(loadTypes loadType,
                                        structures structure,
                                        surfaceProfiles surfaceProfile,
                                        treatments treatment){
        double Kmod = factorForLoadDuration(loadType);
        double Ksp = factorForGlassSurfaceProfile(structure, surfaceProfile);
        double Fbk = characteristicStrengthOfPrestressedGlass(structure, treatment);

        return ((Kmod*Ksp*Fgk)/Yma)+((Kv*(Fbk-Fgk))/(Ymv));
    }

    public static double designStrengthAtEdge(edgeTypes edgeType,
                                              loadTypes loadType,
                                              structures structure,
                                              surfaceProfiles surfaceProfile,
                                              treatments treatment){
        return edgeFactor(edgeType) * designStrength(loadType, structure, surfaceProfile, treatment);
    }

    public enum loadTypes {WIND, PERSONNEL, SNOW, DEADLOAD, SELFWEIGHT};
    public enum treatments {ANNEALED, HEATSTRENGTHENED, THERMALLYTOUGHENED, CHEMICALLYTOUGHENED}
    public enum structures {FLOAT, DRAWN, PATTERNED, ENAMELLED, ENAMELLEDPATTERNED}
    public enum surfaceProfiles {ASPRODUCED, SANDBLASTED}
    public enum edgeTypes {ASCUT, SEAMED, POLISHED }

    public static TableParser characteristicStrengthOfPrestressedGlasses = new TableParser("src/com/seneda/structures/materials/glass_data/characteristic_strength_of_prestressed_glass.csv");
    public static double characteristicStrengthOfPrestressedGlass(structures structure, treatments treatment){
        return characteristicStrengthOfPrestressedGlasses.getValue(structure.toString(), treatment.toString());
    }

    public static TableParser factorForLoadDurations = new TableParser("src/com/seneda/structures/materials/glass_data/factor_for_load_duration.csv");
    public static double factorForLoadDuration(loadTypes loadType){
        return factorForLoadDurations.getValue(loadType.toString(), "");
    }

    public static TableParser factorForGlassSurfaceProfiles = new TableParser("src/com/seneda/structures/materials/glass_data/factor_for_glass_surface_profile.csv");
    public static double factorForGlassSurfaceProfile(structures structure, surfaceProfiles surfaceProfile) {
        return factorForGlassSurfaceProfiles.getValue(structure.toString(), surfaceProfile.toString());
    }

    public static TableParser edgeFactors = new TableParser("src/com/seneda/structures/materials/glass_data/edge_factors.csv");
    public static double edgeFactor(edgeTypes edgeType){
        return edgeFactors.getValue(edgeType.toString(), "");
    };

    public treatments treatment;
    public surfaceProfiles surfaceProfile;
    public structures structure;
    public edgeTypes edgeType;

    public Glass(treatments treatment, surfaceProfiles surfaceProfile, structures structure, edgeTypes edgeType) {
        this.treatment = treatment;
        this.surfaceProfile = surfaceProfile;
        this.structure = structure;
        this.edgeType = edgeType;
    }

    public double designStrengthAtEdge(loadTypes loadType){
        return designStrengthAtEdge(edgeType, loadType, structure, surfaceProfile, treatment);
    }

}
