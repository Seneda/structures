package com.seneda.structures.designs;

import com.seneda.structures.bracket.Bracket;
import com.seneda.structures.cantilever.*;
import com.seneda.structures.glass.Glass;
import com.seneda.structures.glass.Lamination;
import com.seneda.structures.glass.Properties;
//import com.seneda.structures.util.CSVWriter;
import org.apache.commons.lang3.builder.MultilineRecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.lang.reflect.Array;
import java.util.Arrays;


/**
 * Created by seneda on 19/02/17.
 */
public class Balustrade {

    private final Cantilever cantilever;
    private final Bracket bracket;
    public DesignOutput designOutput;

    private class DesignOutput {
        // Inputs
        double glassHeight;
        Properties.Material glassMaterial;
        Properties.SurfaceProfiles glassSurfaceProfile;
        Properties.edgeTypes glassEdgeType;
        Properties.Treatments glassTreatment;
        Properties.BracketMaterials bracketMaterial;
        Properties.LoadTypes[] loadCaseTypes;
        Properties.LoadDurations[] loadDurations;
        double[] loadMagnitudes;

        double[] glassStrenthUnderLoads;
        double maxGlassDeflectionUnderLoad;

        double[] minGlassThicknessesForDeflection;
        double[] minGlassThicknessesForStress;

        double[] laminationLayerThicknesses;
        double[] laminationInterlayerThicknesses;
        // One for each Load
        double[] laminationThicknessForDeflection;
        double[] laminationThicknessForStress;
        double laminationBrokenThicknessForStress;
        double laminationBrokenStress;
        double[] laminationDeflectionUnderLoads;
        double laminationMaxDeflectionUnderLoads;

        //Bracket
        double[] bracketMoments;
        double maxBracketMoment;
        double bracketMinThicknessForDeflection;
        double bracketMinThicknessForStress;
        double bracketChosenThickness;

        public void setBracketDetails(double[] bracketMoments, double maxBracketMoment, double bracketMinThicknessForDeflection, double bracketMinThicknessForStress, double bracketChosenThickness){
            this.bracketMoments = bracketMoments;
            this.maxBracketMoment = maxBracketMoment;
            this.bracketMinThicknessForDeflection = bracketMinThicknessForDeflection;
            this.bracketMinThicknessForStress = bracketMinThicknessForStress;
            this.bracketChosenThickness = bracketChosenThickness;
        }

        public void setCatileverDeflections(double[] laminationDeflectionUnderLoads, double laminationMaxDeflectionUnderLoads) {
            this.laminationDeflectionUnderLoads = laminationDeflectionUnderLoads;
            this.laminationMaxDeflectionUnderLoads = laminationMaxDeflectionUnderLoads;
        }

        public void setLaminationDetails(Lamination lamination){
            this.laminationLayerThicknesses = lamination.layerThicknesses;
            this.laminationInterlayerThicknesses = lamination.interlayerThicknesses;
            this.laminationThicknessForDeflection = new double[loadCaseTypes.length];
            this.laminationThicknessForStress = new double[loadCaseTypes.length];

            for (int i = 0; i < loadCaseTypes.length; i++){
                Lamination.EffectiveThicknesses e = lamination.getEffectiveThicknesses(Lamination.getInterlayerShearModulus(loadDurations[i]));
                this.laminationThicknessForDeflection[i] = e.forDeflection;
                this.laminationThicknessForStress[i] = e.minForStress;
            }
            this.laminationBrokenThicknessForStress = lamination.brokenThickness;
            this.laminationBrokenStress = lamination.brokenStress;
        }

        public void setCantilverRestrictions(double[] glassStrenthUnderLoads,
                                             double maxGlassDeflectionUnderLoad,
                                             double[] minGlassThicknessesForDeflection,
                                             double[] minGlassThicknessesForStress){
            this.glassStrenthUnderLoads = glassStrenthUnderLoads;
            this.maxGlassDeflectionUnderLoad = maxGlassDeflectionUnderLoad;
            this.minGlassThicknessesForDeflection = minGlassThicknessesForDeflection;
            this.minGlassThicknessesForStress = minGlassThicknessesForStress;
        }

        public void setInputs(
                double glassHeight,
                Properties.Material glassMaterial,
                Properties.SurfaceProfiles glassSurfaceProfile,
                Properties.edgeTypes glassEdgeType,
                Properties.Treatments glassTreatment,
                Properties.BracketMaterials bracketMaterial,
                LoadCase[] loadCases){
            this.glassHeight = glassHeight;
            this.glassMaterial = glassMaterial;
            this.glassSurfaceProfile = glassSurfaceProfile;
            this.glassEdgeType = glassEdgeType;
            this.glassTreatment = glassTreatment;
            this.bracketMaterial = bracketMaterial;
            this.loadCaseTypes = new Properties.LoadTypes[loadCases.length];
            this.loadDurations = new Properties.LoadDurations[loadCases.length];
            this.loadMagnitudes = new double[loadCases.length];
            for (int i = 0; i < loadCases.length; i++) {
                this.loadCaseTypes[i] = loadCases[i].loadType;
                this.loadDurations[i] = loadCases[i].loadDuration;
                this.loadMagnitudes[i] = loadCases[i].loadMagnitude;
            }
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("DesignOutput{");
            sb.append(System.lineSeparator()+"glassHeight= ").append(glassHeight);
            sb.append(System.lineSeparator()+" glassMaterial= ").append(glassMaterial);
            sb.append(System.lineSeparator()+" glassSurfaceProfile= ").append(glassSurfaceProfile);
            sb.append(System.lineSeparator()+" glassEdgeType= ").append(glassEdgeType);
            sb.append(System.lineSeparator()+" glassTreatment= ").append(glassTreatment);
            sb.append(System.lineSeparator()+" bracketMaterial= ").append(bracketMaterial);
            sb.append(System.lineSeparator()+" loadCaseTypes= ").append(Arrays.toString(loadCaseTypes));
            sb.append(System.lineSeparator()+" loadDurations= ").append(Arrays.toString(loadDurations));
            sb.append(System.lineSeparator()+" loadMagnitudes= ").append(Arrays.toString(loadMagnitudes));
            sb.append(System.lineSeparator()+" glassStrenthUnderLoads= ").append(Arrays.toString(glassStrenthUnderLoads));
            sb.append(System.lineSeparator()+" maxGlassDeflectionUnderLoad= ").append(maxGlassDeflectionUnderLoad);
            sb.append(System.lineSeparator()+" minGlassThicknessesForDeflection= ").append(Arrays.toString(minGlassThicknessesForDeflection));
            sb.append(System.lineSeparator()+" minGlassThicknessesForStress= ").append(Arrays.toString(minGlassThicknessesForStress));
            sb.append(System.lineSeparator()+" laminationLayerThicknesses= ").append(Arrays.toString(laminationLayerThicknesses));
            sb.append(System.lineSeparator()+" laminationInterlayerThicknesses= ").append(Arrays.toString(laminationInterlayerThicknesses));
            sb.append(System.lineSeparator()+" laminationThicknessForDeflection= ").append(Arrays.toString(laminationThicknessForDeflection));
            sb.append(System.lineSeparator()+" laminationThicknessForStress= ").append(Arrays.toString(laminationThicknessForStress));
            sb.append(System.lineSeparator()+" laminationBrokenThicknessForStress= ").append(laminationBrokenThicknessForStress);
            sb.append(System.lineSeparator()+" laminationBrokenStress= ").append(laminationBrokenStress);
            sb.append(System.lineSeparator()+" laminationDeflectionUnderLoads= ").append(Arrays.toString(laminationDeflectionUnderLoads));
            sb.append(System.lineSeparator()+" laminationMaxDeflectionUnderLoads= ").append(laminationMaxDeflectionUnderLoads);
            sb.append(System.lineSeparator()+" bracketMoments= ").append(Arrays.toString(bracketMoments));
            sb.append(System.lineSeparator()+" maxBracketMoment= ").append(maxBracketMoment);
            sb.append(System.lineSeparator()+" bracketMinThicknessForDeflection= ").append(bracketMinThicknessForDeflection);
            sb.append(System.lineSeparator()+" bracketMinThicknessForStress= ").append(bracketMinThicknessForStress);
            sb.append(System.lineSeparator()+" bracketChosenThickness= ").append(bracketChosenThickness);
            sb.append('}');
            return sb.toString();
        }

//        public String toString() {
//            return new ReflectionToStringBuilder(this, new MultilineRecursiveToStringStyle()).toString();
//        }

    }



    public Balustrade(double glassHeight, Glass glass, LoadCase[] loadCases, double bracketEmbedmentDepth, Properties.BracketMaterials bracketMaterial){

        designOutput = new DesignOutput();
        designOutput.setInputs(glassHeight, glass.material, glass.surfaceProfile, glass.edgeType, glass.treatment, bracketMaterial, loadCases);

        cantilever = new Cantilever(glassHeight, loadCases, glass);
        designOutput.setCantilverRestrictions(cantilever.maxAllowedStress, cantilever.limitingDeflectionUnderLoad, cantilever.minThicknessForDeflection, cantilever.minThicknessForStress);
        designOutput.setLaminationDetails(cantilever.lamination);
        designOutput.setCatileverDeflections(cantilever.deflectionUnderLoad, cantilever.limitingDeflectionUnderLoad);

        bracket = new Bracket(loadCases, bracketEmbedmentDepth, glassHeight, cantilever.limitingDeflectionUnderLoad, bracketMaterial);
        designOutput.setBracketDetails(bracket.moments, bracket.maxMoment, bracket.thicknessForDeflection, bracket.thicknessForStress, bracket.getThickness());
        System.out.println(designOutput);
    }

    public String getSummary(){
        return designOutput.toString();
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

//    public void saveToFile(String filename){
//        CSVWriter file = new CSVWriter(filename);
//
//        file.writeLine("Balustrade Design");
//        file.newLine();
//        writeInputs(file);
//
//        writeCantileverStep(file);
////        writeBracketStep(file);
//
//        file.close();
//
//    }

//    private void writeCantileverStep(CSVWriter file) {
//        file.writeLine("Cantilever");
//        file.writeField("Load Cases");
//        for (int i = 1; i <= cantilever.loadCases.length; i++){
//            file.writeField("Load "+i);
//        }
//        file.newLine();
//        file.writeRow("Max Stress", cantilever.maxAllowedStress);
//        file.writeRow("Deflection Thickness", cantilever.minThicknessForDeflection);
//        file.writeRow("Stress Thickness", cantilever.minThicknessForStress);
////        writeLamination(file);
//    }

//    private void writeLamination(CSVWriter file) {
//        file.writeLine("Lamination");
//        file.writeRow("", new String[]{"t defl", "t str", "broken thickness", "broken stress"});
////        if (cantilever.lamination.failedVersions.size() > 0) {
////            file.writeLine("Failed Laminations");
////
////            for (int i = 0; i < cantilever.lamination.failedVersions.size(); i++) {
////                for (int j = 0; j < cantilever.loadCases.length; j++){
////                    file.write("Load "+(j+1), {cantilever.lamination.failedVersions.get(i).});
////                }
////            }
////        }
//        Lamination l = cantilever.lamination;
//        for (LoadCase L: cantilever.loadCases){
//            file.writeRow(Arrays.toString(l.layerThicknesses),
//                    {l.calcEffectiveThicknessForDeflection(getInterlayerShearModulus(L)),
//                            l.calcEffectiveThicknessForDeflection(getInterlayerShearModulus(L)),);
//        }
//    }

//    private void writeInputs(CSVWriter file) {
//        file.writeLine("Inputs");
//        file.newLine();
//        file.writeRow("Glass Height", new double[]{cantilever.height});
//        file.writeRow("Bracket Embedment Depth", new double[]{BalustradeDesignForm.embedmentDepth});
//        file.writeRow("Glass", new String[] {"Material", "Surface Profile", "Treatment", "Edge Type"});
//        file.writeRow("Glass", new Enum[]{cantilever.glass.material, cantilever.glass.surfaceProfile, cantilever.glass.treatment, cantilever.glass.edgeType});
//        file.newLine();
//        file.writeRow("Bracket Material", new Enum[]{BalustradeDesignForm.material});
//        file.writeRow("Load Cases", new String[]{"Type, Magnitude, Duration"});
//        for (int i = 0; i < cantilever.loadCases.length; i++){
//            LoadCase l = cantilever.loadCases[i];
//            file.writeRow("Load "+(i+1), new Object[]{l.loadType, l.loadMagnitude, l.loadDuration});
//        }
//    }

    public static void main(String[] args){
        double height = 1.1;

        Glass glass = new Glass(Properties.Treatments.THERMALLYTOUGHENED,
                Properties.SurfaceProfiles.ASPRODUCED,
                Properties.Material.FLOAT,
                Properties.edgeTypes.POLISHED);

        LoadCase line = new LineLoad(1.5E3, height, Properties.LoadDurations.LONG_300S);
        LoadCase wind = new WindLoad(1E3, height);
//        LoadCase personnel = new PersonnelLoad(1E3, height);
//        LoadCase crowd = new CrowdLoad(1.5E3, height);
        LoadCase[] loadCases = {line, wind};//, personnel, crowd};
        double embedmentDepth = 0.1;
        Properties.BracketMaterials bracketMaterial = Properties.BracketMaterials.STEEL;

//        double height = 1.1;

//        Glass glass = new Glass(Properties.Treatments.THERMALLYTOUGHENED,
//                Properties.SurfaceProfiles.SANDBLASTED,
//                Properties.Material.FLOAT,
//                Properties.edgeTypes.POLISHED);
//
//        LoadCase line = new LineLoad(1E3, height, Properties.LoadDurations.MID_30S);
//        LoadCase wind = new WindLoad(1E3, height);
////        LoadCase personnel = new PersonnelLoad(1E3, height);
////        LoadCase crowd = new CrowdLoad(1.5E3, height);
//        LoadCase[] loadCases = {line, wind};//, personnel, crowd};
//        double embedmentDepth = 0.1;
//        Properties.BracketMaterials bracketMaterial = Properties.BracketMaterials.ALUMINIUM;


        Balustrade balustrade = new Balustrade(height, glass, loadCases, embedmentDepth, bracketMaterial);


    }
}
