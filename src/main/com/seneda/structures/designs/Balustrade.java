package com.seneda.structures.designs;

import com.seneda.structures.bracket.Bracket;
import com.seneda.structures.cantilever.*;
import com.seneda.structures.glass.Lamination;
import com.seneda.structures.glass.Glass;
import com.seneda.structures.glass.Properties;

import java.util.Arrays;


/**
 * Created by seneda on 19/02/17.
 */
public class Balustrade {

    public Balustrade(double glassHeight, Glass glass, LoadCase[] loadCases, double bracketEmbedmentDepth, Properties.BracketMaterials bracketMaterial){
        Cantilever cantilever = new Cantilever(glassHeight, loadCases, glass);
        Bracket bracket = new Bracket(loadCases, bracketEmbedmentDepth, glassHeight, cantilever.limitingDeflectionUnderLoad, bracketMaterial);

        System.out.println(cantilever);
        System.out.println(bracket);
    }

    public static void main(String[] args){
        double height = 1.1;

        Glass glass = new Glass(Properties.Treatments.THERMALLYTOUGHENED,
                                Properties.SurfaceProfiles.ASPRODUCED,
                                Properties.Material.FLOAT,
                                Properties.edgeTypes.POLISHED);

        LoadCase line = new LineLoad(1.5E3, height, Properties.LoadDurations.MID_30S);
        LoadCase wind = new WindLoad(0.7E3, height);
        LoadCase personnel = new PersonnelLoad(1E3, height);
        LoadCase crowd = new CrowdLoad(1.5E3, height);
        LoadCase[] loadCases = {line, wind, personnel, crowd};
        double embedmentDepth = 0.1;
        Properties.BracketMaterials bracketMaterial = Properties.BracketMaterials.ALUMINIUM;


        Balustrade balustrade = new Balustrade(height, glass, loadCases, embedmentDepth, bracketMaterial);


    }
}
