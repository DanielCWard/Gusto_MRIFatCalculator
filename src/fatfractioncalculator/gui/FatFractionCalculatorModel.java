package fatfractioncalculator.gui;

import fatfractioncalculator.Bounds;
import fatfractioncalculator.CsvWriter;
import fatfractioncalculator.FatVolume;
import fatfractioncalculator.Image;
import fatfractioncalculator.Mask;
import fatfractioncalculator.MaskMriMatcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Daniel Ward
 */
public class FatFractionCalculatorModel {
    
    /* Class Variables */
    private Bounds BATBounds; // Thresholds/Bounds for BAT
    private Bounds WATBounds; // Thresholds/Bounds for WAT
    private Bounds TSABounds; // Thresholds/Bounds for TSA
    private Bounds TIAFBounds; // Thresholds/Bounds for TIAF
    
    private int BATMaskValue; // BAT mask value
    private int WATMaskValue; // WAT mask value
    private int TSAMaskValue; // TSA mask value
    private int TIAFMaskValue; // TIAF mask value
    
    
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
    
    // Default CSV Header
    private String[] headerRow = {"PSCID", 
            "TSA (%) (lower-upper)", 
            "TSA Vol (cm^3)", "TSA abs Min (%)", "TSA mean Min (%)", 
            "TSA abs Max (%)", "TSA mean Max (%)",
            "BAT (%) (lower-upper)", 
            "BAT Vol (cm^3)", "BAT abs Min (%)", "BAT mean Min (%)", 
            "BAT abs Max (%)", "BAT mean Max (%)", 
            "WAT (%) (lower-upper)", 
            "WAT Vol (cm^3)", "WAT abs Min (%)", "WAT mean Min (%)", 
            "WAT abs Max (%)", "WAT mean Max (%)",
            "TIAF (%) (lower-upper)", 
            "TIAF Vol (cm^3)", "TIAF abs Min (%)", "TIAF mean Min (%)", 
            "TIAF abs Max (%)", "TIAF mean Max (%)",
            "Subject Height (m)", "Subject Weight (m)",
            "Sex", "Age", "DOB", "Study ID", "Study Date", "MRI Folder Time",
            "Magnetic Field Strength (T)", "Voxel Height (mm)",
            "Voxel Width (mm)", "Voxel Depth (mm)", "Voxel Volume (mm^2)",
            "MRI Image Path", "Segmentation File Path"};
        
    
    /*
    - function to set CSV and instansiate csvWriter
    - function to set templates and instansiate MRI Matcher
    - Function to set and instansiate the BAT, WAT and TSA bounds
    */
    
    /**
     * Initalise the model to default values
     */
    public FatFractionCalculatorModel() {
        // Set default bounds
        BATBounds = new Bounds(20, 60);
        WATBounds = new Bounds(80, 90);
        TSABounds = new Bounds(20, 100);
        TIAFBounds = new Bounds(0, 100);
        
        BATMaskValue = 1;
        WATMaskValue = 1;
        TSAMaskValue = 1;
        TIAFMaskValue = 2;
        reset();
    }
    
    /**
     * Resets the model calculation file inputs
     */
    public final void reset() {
        segmentationFilePaths = null;
        imagePath = null;
        csvPath = null;
    }
    
    /**
     * Updates and returns the header row with correct bound information
     * @return headerRow for the CSV output file
     */
    private ArrayList<String> getHeaderRow() {
        // Set TSA bounds
        headerRow[1] = "TSA (%) (" + TSABounds.getLower() + "-" + 
                    TSABounds.getUpper() + ")[" + TSAMaskValue + "]";
        // Set BAT Bounds
        headerRow[7] = "BAT (%) (" + BATBounds.getLower() + "-" + 
                    BATBounds.getUpper() + ")[" + BATMaskValue + "]";
        // Set WAT Bounds
        headerRow[13] = "WAT (%) (" + WATBounds.getLower() + "-" + 
                    WATBounds.getUpper() + ")[" + WATMaskValue + "]";
        // Set TIAF Bounds
        headerRow[19] = "TIAF (%) (" + TIAFBounds.getLower() + "-" + 
                    TIAFBounds.getUpper() + ")[" + TIAFMaskValue + "]";
        
        return new ArrayList(Arrays.asList(headerRow));
    }
    
    /**
     * Calculates the statistics of a volume specified by the mask, 
     * maskValue and bounds. The statistics are written to the row.
     * @param image Image to calculate statistics from
     * @param mask mask to mask the image voxels
     * @param row row to write the statistics to
     * @param maskValue maskValue to separate multiple masks
     * @param bounds threshold restrictions on the masked voxels
     */
    private void writeVolumeStatistics(Image image, Mask mask, 
            ArrayList<String> row, int maskValue, Bounds bounds) {
        if (maskValue != -1) {
            FatVolume volume = image.getMaskedVoxelStatistics(mask, bounds, 
                maskValue);
            if (volume.getVoxelCount() != 0) {
                // non empty mask, return to not write NA's
                row.add("" + volume.getAverageValue());
                row.add("" + volume.getVolume(image.getVoxelVolume()));
                row.add("" + volume.getAbsoluteMin());
                row.add("" + volume.getMeanMin());
                row.add("" + volume.getAbsoluteMax());
                row.add("" + volume.getMeanMax());
                return;
            }
        }
        // Not masked not relevent, write NA
        row.add("NA");
        row.add("NA");
        row.add("NA");
        row.add("NA");
        row.add("NA");
        row.add("NA");
    }
    
    /**
     * Runs the calculations for TSA, BAT and WAT and writes the results to the
     * given csv writer
     * @param image image to calculate volumes and statistics
     * @param mask voxels to mask in the image
     * @param csvWriter csv to write results to
     */
    private void calculateAndWriteStatistics(Image image, Mask mask, 
            CsvWriter csvWriter) {
        ArrayList<String> row = new ArrayList<>();
        // Start with Patient ID
        row.add(image.getPatientID());
        // TSA Volume Stats
        writeVolumeStatistics(image, mask, row, TSAMaskValue, TSABounds);
//        FatVolume volume = image.getMaskedVoxelStatistics(mask, TSABounds);
//        row.add("" + volume.getAverageValue());
//        row.add("" + volume.getVolume(image.getVoxelVolume()));
//        row.add("" + volume.getAbsoluteMin());
//        row.add("" + volume.getMeanMin());
//        row.add("" + volume.getAbsoluteMax());
//        row.add("" + volume.getMeanMax());
        // BAT Volume Stats
        writeVolumeStatistics(image, mask, row, BATMaskValue, BATBounds);
//        volume = image.getMaskedVoxelStatistics(mask, BATBounds);
//        row.add("" + volume.getAverageValue());
//        row.add("" + volume.getVolume(image.getVoxelVolume()));
//        row.add("" + volume.getAbsoluteMin());
//        row.add("" + volume.getMeanMin());
//        row.add("" + volume.getAbsoluteMax());
//        row.add("" + volume.getMeanMax());
        // WAT Volume Stats
        writeVolumeStatistics(image, mask, row, WATMaskValue, WATBounds);
//        volume = image.getMaskedVoxelStatistics(mask, WATBounds);
//        row.add("" + volume.getAverageValue());
//        row.add("" + volume.getVolume(image.getVoxelVolume()));
//        row.add("" + volume.getAbsoluteMin());
//        row.add("" + volume.getMeanMin());
//        row.add("" + volume.getAbsoluteMax());
//        row.add("" + volume.getMeanMax());
        
        // TIAF Volume Stats
        writeVolumeStatistics(image, mask, row, TIAFMaskValue, TIAFBounds);
        
        // Patient Details
        row.add("" + image.getPatientHeight());
        row.add("" + image.getPatientWeight());
        row.add("" + image.getPatientSex());
        row.add("" + image.getPatientAge());
        row.add("" + image.getPatientBirthday());
        row.add("" + image.getStudyUID());
        row.add("" + image.getStudyDate());
        row.add("" + image.getFolderTimeStamp());
        row.add("" + image.getMRIStrength());
        row.add("" + image.getVoxelDimensions().getHeight());
        row.add("" + image.getVoxelDimensions().getWidth());
        row.add("" + image.getVoxelDimensions().getDepth());
        row.add("" + image.getVoxelVolume());
        row.add("" + image.getPath());
        row.add("" + mask.getPath());
        
        // Write row to file
        csvWriter.writeRow(row);
    }
    
    /**
     * Runs the calculation given that imagePath contains an image directory
     * not an MRI Parent directory
     * @param csvWriter CSV to write the results to
     * @throws IOException If files are unopenable
     */
    private void runManualCaculation(CsvWriter csvWriter) 
            throws IOException {
        // Write heading row for CSV file
        csvWriter.writeRow(getHeaderRow());
        
        for (String maskPath : segmentationFilePaths) {
            Image image = new Image(imagePath);
            Mask mask = new Mask(maskPath);
            calculateAndWriteStatistics(image, mask, csvWriter);
        }
    }
    
    /**
     * Runs the calculation by locating the matching Image for each mask and the
     * MRI parent directory.
     * @param csvWriter CSV to write the results to
     * @param imageFinder instance of an MaskMriMatcher to locate Images
     * @param view the view to set the progressBar
     * @throws IOException If files are unopenable
     */
    private void runAutomaticCalculation(CsvWriter csvWriter, 
            MaskMriMatcher imageFinder, 
            FatFractionCalculatorView view) throws IOException {
        // Write heading row for CSV file
        csvWriter.writeRow(getHeaderRow());
        
        float progress = 0F;
        view.setProgressBarProgress(progress); // Set progress to 0
        
        for (String maskPath : segmentationFilePaths) {
            Mask mask = new Mask(maskPath);
            Image image = imageFinder.getImageFromMask(mask);
            
            if (image == null) {
                // Failed to find a matching Image
                csvWriter.writeRow(imageFinder.getErrorInformation());
            } else {
                calculateAndWriteStatistics(image, mask, csvWriter);
            }
            
            progress += (1 / segmentationFilePaths.size());
            view.setProgressBarProgress(progress); // set progress to part way
        }
    }
    
    /**
     * Runs the calculations
     * @param view the view to set the progressBar
     * @return false if not possible to run the calculation otherwise true
     */
    public boolean runCalculation(FatFractionCalculatorView view) {
        if (segmentationFilePaths == null || imagePath == null || 
                csvPath == null) {
            return false;
        }
        
        try {
            // Creat CsvWriter
            CsvWriter csvWriter = new CsvWriter(csvPath);
            
            if (Image.isValidImageDirectory(imagePath)) {
                // Manual Mode
                runManualCaculation(csvWriter);
            } else {
                // Automatic mode
                // Create Instance of MRI Matcher
                MaskMriMatcher imageFinder = new MaskMriMatcher(imagePath, 
                        subjectDirectoryTemplate, studyDirectoryTemplate, 
                        imageDirectoryTemplate, segmentationFileTemplate);
                runAutomaticCalculation(csvWriter, imageFinder, view);
            }
            
            // Close the CSV Writer
            csvWriter.close();
                    
        } catch (IOException ex) {
            System.err.println("Bad calculation run: " + ex.getMessage());
            return false;
        }
        
        // Reset Model
        reset();
        
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
     * @return The TSAbounds
     */
    public Bounds getTSABounds() {
        return TSABounds.copy();
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
     * Sets the TSA bounds
     * @param bounds 
     */
    public void setTSABounds(Bounds bounds) {
        TSABounds = bounds;
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
    
    /**
     * Sets the subjectDirectoryTemplate
     * @param subjectDirectoryTemplate 
     */
    public void setSubjectDirectoryTemplate(String subjectDirectoryTemplate) {
        this.subjectDirectoryTemplate = subjectDirectoryTemplate;
    }
    
    /**
     * Sets the studyDirectoryTemplate
     * @param studyDirectoryTemplate 
     */
    public void setStudyDirectoryTemplate(String studyDirectoryTemplate) {
        this.studyDirectoryTemplate = studyDirectoryTemplate;
    }
    
    /**
     * Sets the imageDirectoryTemplate
     * @param imageDirectoryTemplate 
     */
    public void setImageDirectoryTemplate(String imageDirectoryTemplate) {
        this.imageDirectoryTemplate = imageDirectoryTemplate;
    }
    
    /**
     * Sets the segmentationFileTemplate
     * @param segmentationFileTemplate 
     */
    public void setSegmentationFileTemplate(String segmentationFileTemplate) {
        this.segmentationFileTemplate = segmentationFileTemplate;
    }
}
