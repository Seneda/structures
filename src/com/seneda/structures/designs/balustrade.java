package com.seneda.structures.designs;

import com.seneda.structures.cantilever.Lamination;
import com.seneda.structures.materials.Glass;

/**
 * Created by seneda on 19/02/17.
 */
public class balustrade {
    public static void main(String[] args){
        double height, wind_load, line_load, personnel_load;
//        Lamination.layers layers;
        Glass glass = new Glass(Glass.treatments.ANNEALED,
                                Glass.surfaceProfiles.ASPRODUCED,
                                Glass.structures.FLOAT,
                                Glass.edgeTypes.ASCUT);

        double max_deflection, max_stress;

        // Get values for these

        double min_thick_defl_wind, min_thick_defl_line, min_thick_defl_personnel;
        double min_thick_str_wind, min_thick_str_line, min_thick_str_personnel;

        // Get values for these



    }
}
