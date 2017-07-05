/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatfractioncalculator.gui;

import fatfractioncalculator.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * GUI View
 * @author Daniel Ward
 */
public class FatFractionCalculatorView {
    
    /* Class Variables */
    // The view
    private Scene scene; // The scene of the view
    
    private FatFractionCalculatorModel model; // The model
    
    // View Grid
    private GridPane grid;
    
    // Threshold Sliders
    private Slider BATMinSlider; // Slider to select BAT minimum threshold
    private Slider BATMaxSlider; // Slider to select BAT maximum threshold
    private Slider WATMinSlider; // Slider to select WAT minimum threshold
    private Slider WATMaxSlider; // Slider to select WAT maximum threshold
    private Slider TIAFMinSlider; // Slider to select TIAF minimum threshold
    private Slider TIAFMaxSlider; // Slider to select TIAF maximum threshold
    
    // Threshold Labels
    // Label for BAT Min Slider
    private final String BATMinSliderLabel = "BAT Min (%)";
    // Label for BAT Max Slider
    private final String BATMaxSliderLabel = "BAT Max (%)";
    // Label for WAT Min Slider
    private final String WATMinSliderLabel = "WAT Min (%)";
    // Label for WAT Max Slider
    private final String WATMaxSliderLabel = "WAT Max (%)";
    // Label for TIAF Min Slider
    private final String TIAFMinSliderLabel = "Total Interscapular and "
            + "Auxillary Fat Min (%)";
    // Label for TIAF Max Slider
    private final String TIAFMaxSliderLabel = "Total Interscapular and "
            + "Auxillary Fat Max (%)";
    
    //Title
    private final String title = "Fat fraction and volume calculation program";
    
    // Fonts
    private final Font titleFont = Font.font("SanSerif", FontWeight.BOLD, 30);
    private final Font labelFont = Font.font("SanSerif", 22);
    private final Font buttonFont = Font.font("SanSerif", 18);
    
    // Dimensions
    private final int height = 750;
    private final int width = 1000;
    
    // Buttons
    private Button singlePatientManualButton;
    private final String singlePatientManualButtonLabel = "Single Patient\nManual MRI"
            + " Selection";
    private Button singlePatientAutomaticButton;
    private final String singlePatientAutomaticButtonLabel = "Single Patient\n"
            + "Automatic MRI Selection";
    private Button multiPatientAutomaticButton;
    private final String multiPatientAutomaticButtonLabel = "Multiple Patient\n"
            + "Automatic MRI Selection";
    private Button setCsvFileButton;
    private final String setCsvFileButtonLabel = "Set CSV File";
    private Button startCalculationButton;
    private final String startCalculationButtonLabel = "Start Calculation";
    
    // Calculation ProgressBar
    private ProgressBar calculationProgressBar;
    private final String calculationProgressBarLabel = "Calculation Progress";
    
    /**
     * Init the View with the model provided
     * @param model 
     */
    public FatFractionCalculatorView(FatFractionCalculatorModel model) {
        this.model = model;
        
        // Initalise the view grid to pack everything into        
        grid = new GridPane();
        grid.setPrefSize(height, width);
        grid.setMinSize(height, width);   
        grid.setAlignment(Pos.TOP_CENTER);
        
        // Fill the Grid
        populateGridWithTitle();
        populateGridWithSliders();
        populateGridWithButtons();
        populateGridWithProgressBar();
        
        // Set the scene
        scene = new Scene(grid, height, width);
    }
    
    /**
     * Populates the grid with the title
     */
    private void populateGridWithTitle() {
        // Package title in HBox to fit across 2 columns
        HBox hBox = new HBox();
        hBox.setSpacing(5); // Set spacing of 5
        hBox.setPadding(new Insets(10, 10, 10, 10));
    	HBox.setHgrow(hBox, Priority.ALWAYS);
        
        Region pad0 = new Region();
    	Region pad1 = new Region();
        HBox.setHgrow(pad0, Priority.ALWAYS);
        HBox.setHgrow(pad1, Priority.ALWAYS);
        
        Label titleLabel = new Label(title);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setFont(titleFont);
        
        hBox.getChildren().addAll(pad0, titleLabel, pad1);
        grid.add(hBox, 0, 0, 2, 1);
    }
    
    /**
     * Populates the grid with the BAT, WAT and TIAF sliders
     */
    private void populateGridWithSliders() {
        // Add BAT Min at column 0, row 1
        grid.add(createThresholdSlider(BATMinSlider, BATMinSliderLabel), 0, 1);
        // Add BAT Max at column 1, row 1
        grid.add(createThresholdSlider(BATMaxSlider, BATMaxSliderLabel), 1, 1);
        // Add WAT Min at column 0, row 2
        grid.add(createThresholdSlider(WATMinSlider, WATMinSliderLabel), 0, 2);
        // Add WAT Max at column 1, row 2
        grid.add(createThresholdSlider(WATMaxSlider, WATMaxSliderLabel), 1, 2);
        // Add TIAF Min at column 0, row 3
        grid.add(
                createThresholdSlider(TIAFMinSlider, TIAFMinSliderLabel), 0, 3);
        // Add TIAF Max at column 1, row 3
        grid.add(
                createThresholdSlider(TIAFMaxSlider, TIAFMaxSliderLabel), 1, 3);
    }
    
    /**
     * Populates the grid with the following buttons:
     *      single manual process mode
     *      single automatic process mode
     *      multiple automatic process mode
     *      select CSV file
     *      Start calculation 
     */
    private void populateGridWithButtons() {
        // Package buttons in HBox to fit three across 2 columns
        HBox hBox = new HBox();
        hBox.setSpacing(5); // Set spacing of 5
        hBox.setPadding(new Insets(10, 10, 10, 10));
    	HBox.setHgrow(hBox, Priority.ALWAYS);
        
        VBox vBox = new VBox();
        vBox.setSpacing(5); // Set spacing of 5
        vBox.setPadding(new Insets(10, 10, 10, 10));
        VBox.setVgrow(vBox, Priority.ALWAYS);
        
        
        // Single patient manual MRI selection button
        singlePatientManualButton = createButton(
                singlePatientManualButtonLabel);
        
        // Single patient Automatic MRI selection button
        singlePatientAutomaticButton = createButton(
                singlePatientAutomaticButtonLabel);
        
        // Multi patient Automatic MRI selection button
        multiPatientAutomaticButton = createButton(
                multiPatientAutomaticButtonLabel);
        
        Region pad0 = new Region();
    	Region pad1 = new Region();
        HBox.setHgrow(pad0, Priority.ALWAYS);
        HBox.setHgrow(pad1, Priority.ALWAYS);
        
        hBox.getChildren().addAll(pad0, singlePatientManualButton, 
                singlePatientAutomaticButton, multiPatientAutomaticButton, 
                pad1);
        
        // Select CSV Button
        setCsvFileButton = createButton(
                setCsvFileButtonLabel);
        
        // Start Calculation Button
        startCalculationButton = createButton(
                startCalculationButtonLabel);
        
        vBox.getChildren().addAll(hBox, setCsvFileButton, 
                startCalculationButton);
        
        // Add it all to row 4 of the grid spanning all 2 columns and 1 row
        grid.add(vBox, 0, 4, 2, 1);
    }
    
    /**
     * Populates the grid with a progress bar
     */
    private void populateGridWithProgressBar() {
        // Package buttons in HBox to fit three across 2 columns
        HBox hBox = new HBox();
        hBox.setSpacing(5); // Set spacing of 5
        hBox.setPadding(new Insets(10, 10, 10, 10));
    	HBox.setHgrow(hBox, Priority.ALWAYS);
        
        VBox vBox = new VBox();
        vBox.setSpacing(5); // Set spacing of 5
        vBox.setPadding(new Insets(10, 10, 10, 10));
        VBox.setVgrow(vBox, Priority.ALWAYS);
        vBox.setAlignment(Pos.CENTER);
        
        Region pad0 = new Region();
    	Region pad1 = new Region();
        HBox.setHgrow(pad0, Priority.ALWAYS);
        HBox.setHgrow(pad1, Priority.ALWAYS);
        
        // Create progess bar and set to 0% progress
        calculationProgressBar = new ProgressBar();
        calculationProgressBar.setProgress(0F);
        calculationProgressBar.setMinWidth(width * 0.7);
        
        // Create label for the progress Bar
        Label progressBarLabel = new Label(calculationProgressBarLabel);
        progressBarLabel.setFont(labelFont);
        
        hBox.getChildren().addAll(pad0, progressBarLabel, 
                pad1);
        
        vBox.getChildren().addAll(hBox, calculationProgressBar);
        
        // Add it all to row 5 of the grid spanning all 2 columns and 1 row
        grid.add(vBox, 0, 5, 2, 1);
    }
    
    /**
     * Creates and returns a Vertical box which contains the provided slider 
     * which is instansiated with range [0, 100] and snap to ticks of 5.
     * @param slider slider to set
     * @param label label of the slider
     * @return the vertical box containing the slider and its label
     */
    private VBox createThresholdSlider(Slider slider, String label) {
        VBox vBox = new VBox();
        vBox.setSpacing(5); // Set spacing of 5
        vBox.setPadding(new Insets(10, 10, 10, 10));
    	VBox.setVgrow(vBox, Priority.ALWAYS);
        
        // Init slider to have range [0, 100] with spacing 5
        slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setMajorTickUnit(5);
        slider.setMinorTickCount(0);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMinWidth(100);
        slider.setSnapToTicks(true);
        
        // Init label with title and set to label font
        Label sliderLabel = new Label(label);
        sliderLabel.setFont(labelFont);
        
        //Add slider and label to the horizontal box
        vBox.getChildren().addAll(sliderLabel, slider);
        
        vBox.setMinWidth(350);
        vBox.setAlignment(Pos.CENTER);
        
        return vBox;
    }
    
    /**
     * Creates a button of minHeight 60 and provided text
     * @param buttonText button text
     * @return the created button
     */
    private Button createButton(String buttonText) {
        Button button = new Button(buttonText);
    	button.setFont(buttonFont);
    	button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    	button.setMinSize(Double.MIN_VALUE, 60);
    	button.setFocusTraversable(false);
        return button;
    }
    
    /**
     * 
     * @return the Scene of the view
     */
    public Scene getScene() {
        return scene;
    }
    
    
    
    
}
