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

    private final Cantilever cantilever;
    private final Bracket bracket;

    public Balustrade(double glassHeight, Glass glass, LoadCase[] loadCases, double bracketEmbedmentDepth, Properties.BracketMaterials bracketMaterial){

        cantilever = new Cantilever(glassHeight, loadCases, glass);
        bracket = new Bracket(loadCases, bracketEmbedmentDepth, glassHeight, cantilever.limitingDeflectionUnderLoad, bracketMaterial);

        System.out.println(cantilever);
        System.out.println(bracket);
        System.out.println(getSummary(glassHeight, glass, loadCases, bracketEmbedmentDepth, bracketMaterial));
    }

    public String getSummary(double glassHeight, Glass glass, LoadCase[] loadCases, double bracketEmbedmentDepth, Properties.BracketMaterials bracketMaterial){
        String o = "Balustrade Design \n"
                + "\nRequirements\n"
                + String.format("\n\tHeight : %4.2e", glassHeight)
                + String.format("\n\t%s", glass.toString());
        o += "\n\tLoad Cases:";
        for (LoadCase l : loadCases){
            o += String.format("\n\t\t%s", l.toString());
        }
        o += String.format("\n\nOutput Specs\n")
           + String.format("\n\t%s", cantilever.lamination)
           + String.format("\n\t%s", bracket.toString());
        return o;
    // TODO Get a list of all of the numbers which are needed in an output report
        // TODO Look at the combo load things
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
