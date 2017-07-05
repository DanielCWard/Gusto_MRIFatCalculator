package fatfractioncalculator.gui;

import fatfractioncalculator.Bounds;
import fatfractioncalculator.CsvWriter;
import fatfractioncalculator.MaskMriMatcher;
import java.util.List;

/**
 *
 * @author Daniel Ward
 */
public class FatFractionCalculatorModel {
    
    /* Class Variables */
    // mriMatcher to find images from segmentations
    private MaskMriMatcher mriMatcher;
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
    
    // File path lists
    // List of segmentation file Paths
    private List<String> segmentationFilePaths = null;
    // String which can either be an image directory or the 
    // Image parent directory
    private String imagePath = null;
    private String csvPath = null;
    
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
        
        reset();
    }
    
    /**
     * Resets the model calculation file inputs
     */
    public void reset() {
        segmentationFilePaths = null;
        imagePath = null;
        csvPath = null;
    }
    
    /**
     * Runs the calculations
     * @return false if not possible to run the calculation otherwise true
     */
    public boolean runCalculation() {
        if (segmentationFilePaths == null || imagePath == null || 
                csvPath == null) {
            return false;
        }
        
        // Check if imagePath is an Image or need to locate images from their
        // segmentation files
        
        
        
        return true;
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
    
    /**
     * Sets the BAT bounds
     * @param bounds 
     */
    public void setBATBounds(Bounds bounds) {
        BATBounds = bounds;
    }
    
    /**
     * Sets the WAT bounds
     * @param bounds 
     */
    public void setWATBounds(Bounds bounds) {
        WATBounds = bounds;
    }
    
    /**
     * Sets the TIAF bounds
     * @param bounds 
     */
    public void setTIAFBounds(Bounds bounds) {
        TIAFBounds = bounds;
    }
    
    /**
     * Sets the csvPath
     * @param csvPath path to save csv output to
     */
    public void setCsvPath(String csvPath) {
        this.csvPath = csvPath;
    }
    
    /**
     * Sets the imagePath
     * @param imagePath String which can either be an image directory or the 
     * Image parent directory
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    /**
     * Sets the list of segmentation file paths
     * @param segmentationFilePaths list of segmentation file paths
     */
    public void setSegmentationFilePaths(List<String> segmentationFilePaths) {
        this.segmentationFilePaths = segmentationFilePaths;
    }
}
