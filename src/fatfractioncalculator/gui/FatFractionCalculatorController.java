package fatfractioncalculator.gui;

import fatfractioncalculator.Bounds;
import fatfractioncalculator.InvalidBoundsException;
import java.io.File;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author Daniel Ward
 */
public class FatFractionCalculatorController {
    
    /* Class Variables */
    private FatFractionCalculatorModel model; // The model
    private FatFractionCalculatorView view; // The view
    private Stage stage; // The stage
    // boolean to indicate if the template fields are required
    private boolean automaticMode = false;
    
    public FatFractionCalculatorController(FatFractionCalculatorModel model, 
            FatFractionCalculatorView view, Stage stage) {
        this.model = model;
        this.view = view;
        this.stage = stage;
        
        // Set up the GUI
        // Set default templates
        view.setSubjectDirectoryTemplate(model.getSubjectDirectoryTemplate());
        view.setStudyDirectoryTemplate(model.getStudyDirectoryTemplate());
        view.setImageDirectoryTemplate(model.getImageDirectoryTemplate());
        view.setSegmentationFileTemplate(model.getSegmentationFileTemplate());
        
        // Set default bounds
        Bounds bounds = model.getBATBounds();
        view.setBATMinSliderValue(bounds.getLower());
        view.setBATMaxSliderValue(bounds.getUpper());
        
        bounds = model.getWATBounds();
        view.setWATMinSliderValue(bounds.getLower());
        view.setWATMaxSliderValue(bounds.getUpper());
        
        bounds = model.getTIAFBounds();
        view.setTIAFMinSliderValue(bounds.getLower());
        view.setTIAFMaxSliderValue(bounds.getUpper());
        
        // Assign the Handlers to the buttons
        view.setSinglePatientManualButtonHandler(
                new ManualSinglePatientEventActionHandler());
        view.setSetCsvFileButtonHandler(
                new SelectCSVFileEventActionHandler());
        view.setStartCalculationButtonHandler(
                new StartEventActionHandler());
        view.setSinglePatientAutomaticButtonHandler(
                new AutomaticSinglePatientEventActionHandler());
    }
    
    /**
     * Gets the BAT, WAT and TIAF bounds. Checks if they are valid and sets
     * the model accordingly. If bounds are not valid then an error is displayed
     */
    private void getViewSetModelBounds() {
        Bounds BATBounds;
        Bounds WATBounds;
        Bounds TIAFBounds;
        // BAT Bounds
        try {
            BATBounds = new Bounds(view.getBATMinSliderValue(), 
                    view.getBATMaxSliderValue());
        } catch (InvalidBoundsException ex) {
            view.displayErrorAlert("Invalid Bounds!", "Invalid Bounds!", 
                    "BAT Max is not greater than BAT Min.");
            return;
        }
        
        // WAT Bounds
        try {
            WATBounds = new Bounds(view.getWATMinSliderValue(), 
                    view.getWATMaxSliderValue());
        } catch (InvalidBoundsException ex) {
            view.displayErrorAlert("Invalid Bounds!", "Invalid Bounds!", 
                    "WAT Max is not greater than WAT Min.");
            return;
        }
        
        // TIAF Bounds
        try {
            TIAFBounds = new Bounds(view.getTIAFMinSliderValue(), 
                    view.getTIAFMaxSliderValue());
        } catch (InvalidBoundsException ex) {
            view.displayErrorAlert("Invalid Bounds!", "Invalid Bounds!", 
                    "TIAF Max is not greater than TIAF Min.");
            return;
        }
        
        // Bounds are all good from here
        model.setBATBounds(BATBounds);
        model.setWATBounds(WATBounds);
        model.setTIAFBounds(TIAFBounds);
    }
    
    /**
     * Returns the file chosen by the user
     * @param dialogHeader headerText to be displayed in the popup
     * @return file chosen by user
     */
    private File getFileFromUser(String dialogHeader) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(dialogHeader);
        return fileChooser.showOpenDialog(stage);
    }
    
    /**
     * Returns the file of the directory chosen by the user
     * @param dialogHeader headerText to be displayed in the popup
     * @return file of Directory chosen by user
     */
    private File getDirectoryFromUser(String dialogHeader) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(dialogHeader);
        return directoryChooser.showDialog(stage);
    }
    
    /**
     * Returns the file of the directory created by the user
     * @param dialogHeader headerText to be displayed in the popup
     * @return file of Directory chosen by user
     */
    private File getSaveCsvFileFromUser(String dialogHeader) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(dialogHeader);
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("Csv Files", "*.csv"));
        File csvFile = fileChooser.showSaveDialog(stage);
        
        if (csvFile != null && !csvFile.toString().endsWith(".csv")) {
            csvFile = new File(csvFile.toString() + ".csv");
        }
        
        return csvFile;
    }
    
    private void getAndSetTemplates() {
        model.setSubjectDirectoryTemplate(view.getSubjectDirectoryTemplate());
        model.setStudyDirectoryTemplate(view.getStudyDirectoryTemplate());
        model.setImageDirectoryTemplate(view.getImageDirectoryTemplate());
        model.setSegmentationFileTemplate(view.getSegmentationFileTemplate());
        // Prevent further editing
        view.setAllTemplatesEditable(false);
    }
    
    /**
     * EventHandler class for the manual single patient event button
     */
    private class ManualSinglePatientEventActionHandler 
    implements EventHandler<ActionEvent> {
    	/**
         * Override handle to manual single patient button click
         */
        @Override
        public void handle(ActionEvent event) {
            
            // Reset the model
            model.reset();
            
            // template fields not required
            automaticMode = false;
            
            // Set all the template fields to NA and disable editing
            view.setSubjectDirectoryTemplate(
                    "Not required for manual image selection");
            view.setStudyDirectoryTemplate(
                    "Not required for manual image selection");
            view.setImageDirectoryTemplate(
                    "Not required for manual image selection");
            view.setSegmentationFileTemplate(
                    "Not required for manual image selection");
            
            view.setAllTemplatesEditable(false);
            
            // Get the Segmentation file from the user
            File segmentationFile = getFileFromUser(
                    "Select a segmentation file");
            if (segmentationFile == null) {
                view.displayErrorAlert("Invalid segmentation file!", 
                        "Invalid segmentation file!",
                        "Please select a segmenation file ('.nii.gz')");
                return;
            }
            
            // Get the image Directory from the user
            File imageDirectory = getDirectoryFromUser(
                    "Select an image folder");
            if (imageDirectory == null) {
                view.displayErrorAlert("Invalid MRI folder!", 
                        "Invalid MRI folder!",
                        "Please select a MRI folder");
                return;
            }
            
            // Add the image directory and the segmentation Paths to model
            // so it is ready to run after setting CSV file
            ArrayList<String> segmentationPaths = new ArrayList<>();
            segmentationPaths.add(segmentationFile.toString());
            model.setSegmentationFilePaths(segmentationPaths);
            
            model.setImagePath(imageDirectory.toString());
        }
    }
    
    /**
     * EventHandler class for the Select CSV Button
     */
    private class SelectCSVFileEventActionHandler 
    implements EventHandler<ActionEvent> {
    	/**
         * Override handle to Start button click
         */
        @Override
        public void handle(ActionEvent event) {
            
            if (automaticMode) { // Get and set the templates
                getAndSetTemplates();
            }
            
            // Get the Csv file from the user
            File csvFile = getSaveCsvFileFromUser(
                    "Create a csv file");
            if (csvFile == null) {
                view.displayErrorAlert("Invalid csv file!", 
                        "Invalid csv file!",
                        "Please save/create a csv file");
                return;
            }
            
            model.setCsvPath(csvFile.toString());
        }
    }
    
    /**
     * EventHandler class for the Start Button
     */
    private class StartEventActionHandler 
    implements EventHandler<ActionEvent> {
    	/**
         * Override handle to Start button click
         */
        @Override
        public void handle(ActionEvent event) {
            
            getViewSetModelBounds();
            
            if (!model.runCalculation()) {
                view.displayErrorAlert("Calculation Error!", 
                        "Calculation Error!",
                        "Something went wrong");
            } else {
                view.setProgressBarProgress(1F);
            }
        }
    }
    
    /**
     * EventHandler class for the Automatic single patient event button
     */
    private class AutomaticSinglePatientEventActionHandler 
    implements EventHandler<ActionEvent> {
    	/**
         * Override handle to automatic single patient button click
         */
        @Override
        public void handle(ActionEvent event) {
            
            // Reset the model
            model.reset();
            
            // template fields required
            automaticMode = true;
            
            // Get the Segmentation file from the user
            File segmentationFile = getFileFromUser(
                    "Select a segmentation file");
            if (segmentationFile == null) {
                view.displayErrorAlert("Invalid segmentation file!", 
                        "Invalid segmentation file!",
                        "Please select a segmenation file ('.nii.gz')");
                return;
            }
            
            // Get the image Directory from the user
            File imageDirectory = getDirectoryFromUser(
                    "Select MRI folder");
            if (imageDirectory == null) {
                view.displayErrorAlert("Invalid MRI folder!", 
                        "Invalid MRI folder!",
                        "Please select a MRI folder");
                return;
            }
            
            // Add the image directory and the segmentation Paths to model
            // so it is ready to run after setting CSV file
            ArrayList<String> segmentationPaths = new ArrayList<>();
            segmentationPaths.add(segmentationFile.toString());
            model.setSegmentationFilePaths(segmentationPaths);
            
            model.setImagePath(imageDirectory.toString());
        }
    }
}
