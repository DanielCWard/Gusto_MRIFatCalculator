package fatfractioncalculator.gui;

import fatfractioncalculator.Bounds;
import fatfractioncalculator.CsvWriter;
import fatfractioncalculator.MaskMriMatcher;

/**
 *
 * @author Daniel Ward
 */
public class FatFractionCalculatorModel {
    
    /* Class Variables */
    private MaskMriMatcher mriMatcher; // mriMatcher to find images from segmentations
    private CsvWriter csvWriter; // CSV file
    private Bounds BATBounds; // Thresholds/Bounds for BAT
    private Bounds WATBounds; // Thresholds/Bounds for WAT
    private Bounds TIAFBounds; // Thresholds/Bounds for TIAF
    
    // Default values for templates
    private String subjectDirectoryTemplate = "GUSTO_6YR_";
    private String studyDirectoryTemplate = "GUSTO_GUSTO_RESEARCH_20160403_,"
            + " GUSTO_GUSTO_RESEARCH_2";
    private String imageDirectoryTemplate = 
            "AX_VIBE_6ECHOES_SCAPULA_LL2_RR_FF_////, "
            + "AX_VIBE_6ECHOES_SCAPULA_LL2_FF_////, "
            + "AX_VIBE_6ECHOES_BAT_LL2_SMALL_RR_FF_////, "
            + "AX_VIBE_6ECHOES_BAT2_LL2_SMALL_FF_////";
    private String segmentationFileTemplate = "/////////_BAT6.nii.gz";
    
    /*
    - function to set CSV and instansiate csvWriter
    - function to set templates and instansiate MRI Matcher
    - Function to set and instansiate the BAT, WAT and TIAF bounds
    */
    
    /**
     * Initalise the model to default values
     */
    public FatFractionCalculatorModel() {
        // Set default bounds
        BATBounds = new Bounds(20, 60);
        WATBounds = new Bounds(80, 90);
        TIAFBounds = new Bounds(20, 100);
    }
    
    /**
     * 
     * @return the subjectDirectoryTemplate
     */
    public String getSubjectDirectoryTemplate() {
        return subjectDirectoryTemplate;
    }
    
    /**
     * 
     * @return the studyDirectoryTemplate
     */
    public String getStudyDirectoryTemplate() {
        return studyDirectoryTemplate;
    }
    
    /**
     * 
     * @return the imageDirectoryTemplate
     */
    public String getImageDirectoryTemplate() {
        return imageDirectoryTemplate;
    }
    
    /**
     * 
     * @return the segmentationFileTemplate
     */
    public String getSegmentationFileTemplate() {
        return segmentationFileTemplate;
    }
    
    /**
     * 
     * @return The BATbounds
     */
    public Bounds getBATBounds() {
        return BATBounds.copy();
    }
    
    /**
     * 
     * @return The WATbounds
     */
    public Bounds getWATBounds() {
        return WATBounds.copy();
    }
    
    /**
     * 
     * @return The TIAFbounds
     */
    public Bounds getTIAFBounds() {
        return TIAFBounds.copy();
    }
}
