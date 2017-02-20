package com.seneda.structures.designs;

import com.seneda.structures.cantilever.Loading;
import com.seneda.structures.cantilever.lamination;
import com.seneda.structures.materials.Glass;
import com.seneda.structures.materials.glass_data.GlassProperties;

import java.util.Arrays;

import static com.seneda.structures.cantilever.Loading.thicknessMinDeflection;
import static com.seneda.structures.cantilever.Loading.thicknessMinStress;

/**
 * Created by seneda on 19/02/17.
 */
public class balustrade {
    public static void main(String[] args){
        double height, line_load, interlayerShearModulus;
        height = 1.1;
        interlayerShearModulus = 0.8E6;
        line_load = 1.5E3;
//        Lamination.layers layers;
        Glass glass = new Glass(GlassProperties.Treatments.THERMALLYTOUGHENED,
                                GlassProperties.SurfaceProfiles.ASPRODUCED,
                                GlassProperties.Material.FLOAT,
                                GlassProperties.edgeTypes.POLISHED);

        double max_deflection, max_stress;
        max_stress = glass.designStrengthAtEdge(GlassProperties.LoadTypes.LINE);
        max_deflection = 25E-3;

        double min_thick_defl_line;
        double min_thick_str_line;

        min_thick_defl_line = thicknessMinDeflection(height, line_load, GlassProperties.YoungsModulus, max_deflection);
        double ULSLoadFactor = 1.5;
        min_thick_str_line = thicknessMinStress(height, ULSLoadFactor*line_load, max_stress);

        System.out.println("Min Thick Defl : "+min_thick_defl_line);
        System.out.println("Min Thick str : "+min_thick_str_line);

        lamination l = lamination.findSufficientLamination(min_thick_defl_line, min_thick_str_line, height, interlayerShearModulus);

        System.out.println("Chosen Lamination is  : "+ Arrays.toString(l.layerThicknesses));

        double effective_thickness_for_deflection = new Double(l.getEffectiveThicknesses(interlayerShearModulus).forDeflection);

        System.out.println("Effective Thickness for deflection is : " + effective_thickness_for_deflection);

        double actual_deflection = Loading.deflectionFromThickness(line_load, GlassProperties.YoungsModulus, effective_thickness_for_deflection, height);

        System.out.println("Actual Deflection is now : " + actual_deflection);
    }
}
