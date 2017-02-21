package com.seneda.structures.glass;

import com.seneda.structures.util.ListReader;
import com.seneda.structures.util.TableReader;

import java.io.FileNotFoundException;

/**
 * Created by seneda on 20/02/17.
 */
public class Properties {
    public static final String dataPath = "src/main/com/seneda/structures/glass/glass_data";

    public static final TableReader generalConstants = loadTable("general_constants.csv");
    public static final double YoungsModulus = generalConstants.get("Youngs Modulus");
    public static final double CharacteristicStrengthOfBasicAnnealedGlass = generalConstants.get("Characteristic Strength of Basic Annealed Glass");
    public static final double MaterialPartialFactorBasicAnnealedGlass = generalConstants.get("Material Partial Factor For Basic Annealed Glass");
    public static final double MaterialPartialFactorPrestressedGlass = generalConstants.get("Material Partial Factor For Prestressed Glass");
    public static final double StrengtheningFactor = generalConstants.get("Strengthening Factor");
    public static final double maxDeflection = generalConstants.get("Maximum Deflection");
    public static final double ULSFactor = generalConstants.get("ULS Factor");

    public static enum LoadTypes {WIND, LINE, PERSONNEL, SNOW, DEADLOAD, SELFWEIGHT};
    public static enum LoadDurations {SHORT_3S, MID_30S, LONG_300S};
    public static enum Treatments {ANNEALED, HEATSTRENGTHENED, THERMALLYTOUGHENED, CHEMICALLYTOUGHENED}
    public static enum Material {FLOAT, DRAWN, PATTERNED, ENAMELLED, ENAMELLEDPATTERNED}
    public static enum SurfaceProfiles {ASPRODUCED, SANDBLASTED}
    public static enum edgeTypes {ASCUT, SEAMED, POLISHED }

    public static final TableReader LoadDurationFactor = loadTable("factor_for_load_duration.csv");
    public static final TableReader CharacteristicStrengthOfPrestressedGlass = loadTable("characteristic_strength_of_prestressed_glass.csv") ;
    public static final TableReader FactorForGlassSurfaceProfile = loadTable("factor_for_glass_surface_profile.csv");
    public static final TableReader EdgeFactor = loadTable("edge_factor.csv");
    public static final TableReader InterlayerShearModulus = loadTable("shear_modulii.csv");


    public static final double[] availableSheetThicknesses = loadList("available_sheet_thicknesses.csv");
    public static final double[] availableInterlayerThicknesses = loadList("available_interlayer_thicknesses.csv");

    private static double[] loadList(String filename) {
        try {
            ListReader listReader = new ListReader(filename, dataPath);
            return listReader.toArray();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Data file Missing : " + filename + e.toString());
        }
    }

    public static TableReader loadTable(String filename) {
        try {
            return new TableReader(filename, dataPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Data file Missing : " + filename + e.toString());
        }
    }
}
