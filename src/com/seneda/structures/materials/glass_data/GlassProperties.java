package com.seneda.structures.materials.glass_data;

import com.seneda.structures.LoadingTest;
import com.seneda.structures.util.TableReader;

import java.io.FileNotFoundException;

/**
 * Created by seneda on 20/02/17.
 */
public class GlassProperties {
    public static final String dataPath = "src/com/seneda/structures/materials/glass_data";

    public static final TableReader generalConstants = loadTable("general_constants.csv");
    public static final double YoungsModulus = generalConstants.get("Youngs Modulus");
    public static final double CharacteristicStrengthOfBasicAnnealedGlass = generalConstants.get("Characteristic Strength of Basic Annealed Glass");
    public static final double MaterialPartialFactorBasicAnnealedGlass = generalConstants.get("Material Partial Factor For Basic Annealed Glass");
    public static final double MaterialPartialFactorPrestressedGlass = generalConstants.get("Material Partial Factor For Prestressed Glass");
    public static final double StrengtheningFactor = generalConstants.get("Strengthening Factor");

    public static enum loadTypes {WIND, LINE, PERSONNEL, SNOW, DEADLOAD, SELFWEIGHT};
    public static enum treatments {ANNEALED, HEATSTRENGTHENED, THERMALLYTOUGHENED, CHEMICALLYTOUGHENED}
    public static enum structures {FLOAT, DRAWN, PATTERNED, ENAMELLED, ENAMELLEDPATTERNED}
    public static enum surfaceProfiles {ASPRODUCED, SANDBLASTED}
    public static enum edgeTypes {ASCUT, SEAMED, POLISHED }

    public static final TableReader LoadDurationFactors = loadTable("factor_for_load_duration.csv");
    public static final TableReader CharacteristicStrengthOfPrestressedGlass = loadTable("characteristic_strength_of_prestressed_glass.csv") ;
    public static final TableReader FactorForGlassSurfaceProfile = loadTable("factor_for_glass_surface_profile.csv");
    public static final TableReader EdgeFactor = loadTable("edge_factor.csv");


    public static TableReader loadTable(String filename) {
        try {
            return new TableReader(filename, dataPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Data file Missing : " + filename + e.toString());
        }
    }
}
