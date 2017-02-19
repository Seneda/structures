package com.seneda.structures.materials;


/**
 * Created by seneda on 18/02/17.
 */
public class Glass {
    static double Fgk = 45E+6;  // CHARACTERISTIC STRENGTH OF BASIC ANNEALED GLASS, Always 45N/mm2
    static double Yma = 1.6;    // MATERIAL PARTIAL FACTOR FOR BASIC ANNEALED GLASS, Always 1.6
    static double Ymv = 1.2;    // MATERIAL PARTIAL FACTOR FOR PRESTRESSED GLASS, Always 1.2
    static double Kv  = 1;      // STRENGTHENING FACTOR, Always 1


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

    public static DataTableParser characteristicStrengthOfPrestressedGlasses = new DataTableParser("src/com/seneda/structures/materials/glass_data/characteristic_strength_of_prestressed_glass.csv");
    public static double characteristicStrengthOfPrestressedGlass(structures structure, treatments treatment){
        return characteristicStrengthOfPrestressedGlasses.getValue(structure.toString(), treatment.toString());
    }

    public static DataTableParser factorForLoadDurations = new DataTableParser("src/com/seneda/structures/materials/glass_data/factor_for_load_duration.csv");
    public static double factorForLoadDuration(loadTypes loadType){
        return factorForLoadDurations.getValue(loadType.toString(), "");
    }

    public static DataTableParser factorForGlassSurfaceProfiles = new DataTableParser("src/com/seneda/structures/materials/glass_data/factor_for_glass_surface_profile.csv");
    public static double factorForGlassSurfaceProfile(structures structure, surfaceProfiles surfaceProfile) {
        return factorForGlassSurfaceProfiles.getValue(structure.toString(), surfaceProfile.toString());
    }

    public static DataTableParser edgeFactors = new DataTableParser("src/com/seneda/structures/materials/glass_data/edge_factors.csv");
    public static double edgeFactor(edgeTypes edgeType){
        return edgeFactors.getValue(edgeType.toString(), "");
    };

}
