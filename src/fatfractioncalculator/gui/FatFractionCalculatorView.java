package fatfractioncalculator.gui;

import fatfractioncalculator.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
    private Slider BATSAMinSlider; // Slider to select BAT SA minimum threshold
    private Slider BATSAMaxSlider; // Slider to select BAT SA maximum threshold
    private Slider BATISMinSlider; // Slider to select BAT IS minimum threshold
    private Slider BATISMaxSlider; // Slider to select BAT IS maximum threshold
    private Slider WATSAMinSlider; // Slider to select WAT SA minimum threshold
    private Slider WATSAMaxSlider; // Slider to select WAT SA maximum threshold
    private Slider WATISMinSlider; // Slider to select WAT IS minimum threshold
    private Slider WATISMaxSlider; // Slider to select WAT IS maximum threshold
    private Slider TSAMinSlider; // Slider to select TSA minimum threshold
    private Slider TSAMaxSlider; // Slider to select TSA maximum threshold
    private Slider TISMinSlider; // Slider to select TIS minimum threshold
    private Slider TISMaxSlider; // Slider to select TIS maximum threshold
    
    // Threshold Labels
    // Label for BAT SA Min Slider
    private final String BATSAMinSliderLabel = "BAT SA Min (%)";
    // Label for BAT SA Max Slider
    private final String BATSAMaxSliderLabel = "BAT SA Max (%)";
 // Label for BAT IS Min Slider
    private final String BATISMinSliderLabel = "BAT IS Min (%)";
    // Label for BAT IS Max Slider
    private final String BATISMaxSliderLabel = "BAT IS Max (%)";
    
    // Label for WAT SA Min Slider
    private final String WATSAMinSliderLabel = "WAT SA Min (%)";
    // Label for WAT SA Max Slider
    private final String WATSAMaxSliderLabel = "WAT SA Max (%)";
 // Label for WAT Min IS Slider
    private final String WATISMinSliderLabel = "WAT IS Min (%)";
    // Label for WAT Max IS Slider
    private final String WATISMaxSliderLabel = "WAT IS Max (%)";
    
    // Label for TSA Min Slider
    private final String TSAMinSliderLabel = "TSA Min (%)";
    // Label for TSA Max Slider
    private final String TSAMaxSliderLabel = "TSA Max (%)";
    // Label for TIS Min Slider
    private final String TISMinSliderLabel = "TIS Min (%)";
    // Label for TIS Max Slider
    private final String TISMaxSliderLabel = "TIS Max (%)";
    
    //Title
    private final String title = "Fat fraction and volume calculation program";
    
    // Fonts
    private final Font titleFont = Font.font("SanSerif", FontWeight.BOLD, 30);
    private final Font labelFont = Font.font("SanSerif", 22);
    private final Font buttonFont = Font.font("SanSerif", 18);
    
    // Dimensions
    private final int height = 1000;
    private final int width = 1700;
    
    // Buttons
    private Button singlePatientManualButton;
    private final String singlePatientManualButtonLabel = "Single Patient\n"
            + "Manual MRI Selection";
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
    
    // Template Fields
    private TextField subjectDirectoryTemplate;
    private final String subjectDirectoryTemplateLabel = "Subject directory "
            + "template";
    private TextField studyDirectoryTemplate;
    private final String studyDirectoryTemplateLabel = "Study directory "
            + "template";
    private TextField imageDirectoryTemplate;
    private final String imageDirectoryTemplateLabel = "MRI directory "
            + "template";
    private TextField segmentationFileTemplate;
    private final String segmentationFileTemplateLabel = "Segmentation File "
            + "template";
    
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
        populateGridWithTemplateFields();
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
     * Populates the grid with the BAT, WAT and TSA sliders
     */
    private void populateGridWithSliders() {
    	VBox batSliders = new VBox();
    	// BAT SA 
        HBox batSASliders = new HBox();
        BATSAMinSlider = new Slider();
        BATSAMaxSlider = new Slider();
        // Pack the two sliders into an HBox side by side
        batSASliders.getChildren().addAll(createThresholdSlider(BATSAMinSlider, 
                    BATSAMinSliderLabel), 
                createThresholdSlider(BATSAMaxSlider, BATSAMaxSliderLabel));
        HBox.setHgrow(batSASliders, Priority.ALWAYS);
        
        // BAT IS
        HBox batISSliders = new HBox();
        BATISMinSlider = new Slider();
        BATISMaxSlider = new Slider();
        // Pack the two sliders into an HBox side by side
        batISSliders.getChildren().addAll(createThresholdSlider(BATISMinSlider, 
                    BATISMinSliderLabel), 
                createThresholdSlider(BATISMaxSlider, BATISMaxSliderLabel));
        HBox.setHgrow(batISSliders, Priority.ALWAYS);
        
        // Put both sets of sliders in the vbox
        batSliders.getChildren().addAll(batSASliders, batISSliders);
        // Put VBox in the grid across all 2 rows
        grid.add(batSliders, 0, 1, 2, 1);
        
        VBox watSliders = new VBox();        
        // WAT SA
        HBox watSASliders = new HBox();
        WATSAMinSlider = new Slider();
        WATSAMaxSlider = new Slider();
        // Pack the two sliders into an HBox side by side
        watSASliders.getChildren().addAll(createThresholdSlider(WATSAMinSlider, 
                    WATSAMinSliderLabel), 
                createThresholdSlider(WATSAMaxSlider, WATSAMaxSliderLabel));
        HBox.setHgrow(watSASliders, Priority.ALWAYS);
        
        // WAT IS
        HBox watISSliders = new HBox();
        WATISMinSlider = new Slider();
        WATISMaxSlider = new Slider();
        // Pack the two sliders into an HBox side by side
        watISSliders.getChildren().addAll(createThresholdSlider(WATISMinSlider, 
                    WATISMinSliderLabel), 
                createThresholdSlider(WATISMaxSlider, WATISMaxSliderLabel));
        HBox.setHgrow(watISSliders, Priority.ALWAYS);
        
        // Put both sets of sliders in the vbox
        watSliders.getChildren().addAll(watSASliders, watISSliders);
        // Put VBox in the grid across all 2 rows
        grid.add(watSliders, 0, 2, 2, 1);
        
        HBox TSASliders = new HBox();
        TSAMinSlider = new Slider();
        TSAMaxSlider = new Slider();
        // Pack the two sliders into an HBox side by side
        TSASliders.getChildren().addAll(createThresholdSlider(TSAMinSlider, 
                    TSAMinSliderLabel), 
                createThresholdSlider(TSAMaxSlider, TSAMaxSliderLabel));
        HBox.setHgrow(TSASliders, Priority.ALWAYS);
        // Put HBox in the grid across all 2 rows
        grid.add(TSASliders, 0, 3, 2, 1);
        
        HBox TISSliders = new HBox();
        TISMinSlider = new Slider();
        TISMaxSlider = new Slider();
        // Pack the two sliders into an HBox side by side
        TISSliders.getChildren().addAll(createThresholdSlider(TISMinSlider, 
                    TISMinSliderLabel), 
                createThresholdSlider(TISMaxSlider, TISMaxSliderLabel));
        HBox.setHgrow(TISSliders, Priority.ALWAYS);
        // Put HBox in the grid across all 2 rows
        grid.add(TISSliders, 0, 4, 2, 1);
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
        
        // Add it all to row 5 of the grid spanning all 2 columns and 1 row
        grid.add(hBox, 0, 5, 2, 1);
        
        // Select CSV Button
        setCsvFileButton = createButton(
                setCsvFileButtonLabel);
        
        // Start Calculation Button
        startCalculationButton = createButton(
                startCalculationButtonLabel);
        
        vBox.getChildren().addAll(setCsvFileButton, 
                startCalculationButton);
        
        // Add it all to row 7 of the grid spanning all 2 columns and 1 row
        grid.add(vBox, 0, 7, 2, 1);
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
        calculationProgressBar.setMinWidth(width * 0.7);
        setProgressBarProgress(0F);
        
        // Create label for the progress Bar
        Label progressBarLabel = new Label(calculationProgressBarLabel);
        progressBarLabel.setFont(labelFont);
        
        hBox.getChildren().addAll(pad0, progressBarLabel, 
                pad1);
        
        vBox.getChildren().addAll(hBox, calculationProgressBar);
        
        // Add it all to row 8 of the grid spanning all 2 columns and 1 row
        grid.add(vBox, 0, 8, 2, 1);
    }
    
    /**
     * Populates the grid with the template fields.
     */
    private void populateGridWithTemplateFields() {
        // Package fields and labels in VBox
        VBox vBox = new VBox();
        vBox.setSpacing(5); // Set spacing of 5
        vBox.setPadding(new Insets(10, 10, 10, 10));
        VBox.setVgrow(vBox, Priority.ALWAYS);
        vBox.setAlignment(Pos.CENTER);
        
        // Templates
        subjectDirectoryTemplate = new TextField();
        HBox subjectDirectory = createTemplateTextField(
                subjectDirectoryTemplate, subjectDirectoryTemplateLabel);
        
        studyDirectoryTemplate = new TextField();
        HBox studyDirectory = createTemplateTextField(
                studyDirectoryTemplate, studyDirectoryTemplateLabel);
        imageDirectoryTemplate = new TextField();
        HBox imageDirectory = createTemplateTextField(
                imageDirectoryTemplate, imageDirectoryTemplateLabel);
        segmentationFileTemplate = new TextField();
        HBox segmentationFile = createTemplateTextField(
                segmentationFileTemplate, segmentationFileTemplateLabel);
        
        vBox.getChildren().addAll(subjectDirectory, studyDirectory, 
                imageDirectory, segmentationFile);
        
        // Add to grid at column 0 row 6
        grid.add(vBox, 0, 6, 2, 1);
    }
    
    /**
     * Creates an HBox filled with a text field and its label
     * @param textField
     * @param label
     * @return the HBox
     */
    private HBox createTemplateTextField(TextField textField, String label) {
        // Package field and label in HBox to fit across 2 columns
        HBox hBox = new HBox();
        hBox.setSpacing(5); // Set spacing of 5
        hBox.setPadding(new Insets(10, 10, 10, 10));
    	HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setMinWidth(width * 0.7);
        
        // Pad between the text field and the label
        Region pad0 = new Region();
        HBox.setHgrow(pad0, Priority.SOMETIMES);
        
        // Init label with title and set to label font
        Label fieldLabel = new Label(label);
        fieldLabel.setFont(labelFont);
        
    	textField.setEditable(true);
    	textField.setAlignment(Pos.CENTER_LEFT);
    	textField.setFocusTraversable(false);
    	textField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    	textField.setMinSize(500, Double.MIN_VALUE);
    	textField.setPrefSize(500, 30);
        
        hBox.getChildren().addAll(fieldLabel, pad0, textField);
        
    	return hBox;
    }
    
    /**
     * Creates and returns a Vertical box which contains the provided slider 
     * which is set to range [0, 100] and snap to ticks of 5.
     * @param slider slider to set
     * @param label label of the slider
     * @return the vertical box containing the slider and its label
     */
    private VBox createThresholdSlider(Slider slider, String label) {
        VBox vBox = new VBox();
        vBox.setSpacing(5); // Set spacing of 5
        vBox.setPadding(new Insets(10, 10, 10, 10));
    	VBox.setVgrow(vBox, Priority.ALWAYS);
        HBox.setHgrow(vBox, Priority.ALWAYS);
        
        // Init slider to have range [0, 100] with spacing 5
        slider.setMin(0);
        slider.setMax(100);
        slider.setMajorTickUnit(5);
        slider.setMinorTickCount(5);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMinWidth(300);
        slider.setSnapToTicks(true);
        HBox.setHgrow(slider, Priority.ALWAYS);
        
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
     * Sets the value of the BAT SA min threshold
     * @param value the value to set the slider to
     */
    public void setBATSAMinSliderValue(double value) {
        BATSAMinSlider.setValue(value);
    }
    
    /**
     * Sets the value of the BAT SA max threshold
     * @param value the value to set the slider to
     */
    public void setBATSAMaxSliderValue(double value) {
        BATSAMaxSlider.setValue(value);
    }
    
    /**
     * Sets the value of the BAT IS min threshold
     * @param value the value to set the slider to
     */
    public void setBATISMinSliderValue(double value) {
        BATISMinSlider.setValue(value);
    }
    
    /**
     * Sets the value of the BAT IS max threshold
     * @param value the value to set the slider to
     */
    public void setBATISMaxSliderValue(double value) {
        BATISMaxSlider.setValue(value);
    }
    
    /**
     * Sets the value of the WAT SA min threshold
     * @param value the value to set the slider to
     */
    public void setWATSAMinSliderValue(double value) {
        WATSAMinSlider.setValue(value);
    }
    
    /**
     * Sets the value of the WAT SA max threshold
     * @param value the value to set the slider to
     */
    public void setWATSAMaxSliderValue(double value) {
        WATSAMaxSlider.setValue(value);
    }
    
    /**
     * Sets the value of the WAT IS min threshold
     * @param value the value to set the slider to
     */
    public void setWATISMinSliderValue(double value) {
        WATISMinSlider.setValue(value);
    }
    
    /**
     * Sets the value of the WAT IS max threshold
     * @param value the value to set the slider to
     */
    public void setWATISMaxSliderValue(double value) {
        WATISMaxSlider.setValue(value);
    }
    
    /**
     * Sets the value of the TSA min threshold
     * @param value the value to set the slider to
     */
    public void setTSAMinSliderValue(double value) {
        TSAMinSlider.setValue(value);
    }
    
    /**
     * Sets the value of the TSA max threshold
     * @param value the value to set the slider to
     */
    public void setTSAMaxSliderValue(double value) {
        TSAMaxSlider.setValue(value);
    }
    
    /**
     * Sets the value of the TIS max threshold
     * @param value the value to set the slider to
     */
    public void setTISMaxSliderValue(double value) {
        TISMaxSlider.setValue(value);
    }
    
    /**
     * Sets the value of the IS min threshold
     * @param value the value to set the slider to
     */
    public void setTISMinSliderValue(double value) {
        TISMinSlider.setValue(value);
    }
    
    /**
     * Sets the template of the subjectDirectory template
     * @param template the template to set
     */
    public void setSubjectDirectoryTemplate(String template) {
        subjectDirectoryTemplate.setText(template);
    }
    
    /**
     * Sets the template of the study directory template
     * @param template the template to set
     */
    public void setStudyDirectoryTemplate(String template) {
        studyDirectoryTemplate.setText(template);
    }
    
    /**
     * Sets the template of the image directory template
     * @param template the template to set
     */
    public void setImageDirectoryTemplate(String template) {
        imageDirectoryTemplate.setText(template);
    }
    
    /**
     * Sets the template of the segmenation file template
     * @param template the template to set
     */
    public void setSegmentationFileTemplate(String template) {
        segmentationFileTemplate.setText(template);
    }
    
    /**
     * Sets the editing permissions of all template fields
     * @param allowed editing permissions
     */
    public void setAllTemplatesEditable(boolean allowed) {
        subjectDirectoryTemplate.setEditable(allowed);
        studyDirectoryTemplate.setEditable(allowed);
        imageDirectoryTemplate.setEditable(allowed);
        segmentationFileTemplate.setEditable(allowed);
    }
    
    /**
     * Sets the click handler for the singlePatientManualButton
     * @param handler handler to assign to the button
     */
    public void setSinglePatientManualButtonHandler(
            EventHandler<ActionEvent> handler) {
        singlePatientManualButton.setOnAction(handler);
    }
    
    /**
     * Sets the click handler for the singlePatientAutomaticButton
     * @param handler handler to assign to the button
     */
    public void setSinglePatientAutomaticButtonHandler(
            EventHandler<ActionEvent> handler) {
        singlePatientAutomaticButton.setOnAction(handler);
    }
    
    /**
     * Sets the click handler for the singlePatientAutomaticButton
     * @param handler handler to assign to the button
     */
    public void setMultiPatientAutomaticButtonHandler(
            EventHandler<ActionEvent> handler) {
        multiPatientAutomaticButton.setOnAction(handler);
    }
    
    /**
     * Sets the click handler for the setCsvFileButton
     * @param handler handler to assign to the button
     */
    public void setSetCsvFileButtonHandler(
            EventHandler<ActionEvent> handler) {
        setCsvFileButton.setOnAction(handler);
    }
    
    /**
     * Sets the click handler for the startCalculationButton
     * @param handler handler to assign to the button
     */
    public void setStartCalculationButtonHandler(
            EventHandler<ActionEvent> handler) {
        startCalculationButton.setOnAction(handler);
    }
    
    /**
     * Sets the calculation progress bar progress
     * @param progress value to set the progress bar to
     */
    public void setProgressBarProgress(float progress) {
        calculationProgressBar.setProgress(progress);
    }
    
    /**
     * 
     * @return the value of the BAT SA min threshold
     */
    public int getBATSAMinSliderValue() {
        return (int) BATSAMinSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the BAT SA max threshold
     */
    public int getBATSAMaxSliderValue() {
        return (int) BATSAMaxSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the BAT IS min threshold
     */
    public int getBATISMinSliderValue() {
        return (int) BATISMinSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the BAT IS max threshold
     */
    public int getBATISMaxSliderValue() {
        return (int) BATISMaxSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the WAT SA min threshold
     */
    public int getWATSAMinSliderValue() {
        return (int) WATSAMinSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the WAT SA max threshold
     */
    public int getWATSAMaxSliderValue() {
        return (int) WATSAMaxSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the WAT IS min threshold
     */
    public int getWATISMinSliderValue() {
        return (int) WATISMinSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the WAT IS max threshold
     */
    public int getWATISMaxSliderValue() {
        return (int) WATISMaxSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the TSA min threshold
     */
    public int getTSAMinSliderValue() {
        return (int) TSAMinSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the TSA max threshold
     */
    public int getTSAMaxSliderValue() {
        return (int) TSAMaxSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the TIS min threshold
     */
    public int getTISMinSliderValue() {
        return (int) TISMinSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the TIS max threshold
     */
    public int getTISMaxSliderValue() {
        return (int) TISMaxSlider.getValue();
    }
    
    /**
     * 
     * @return the subjectDirectoryTemplate
     */
    public String getSubjectDirectoryTemplate() {
        return subjectDirectoryTemplate.getText();
    }
    
    /**
     * 
     * @return the studyDirectoryTemplate
     */
    public String getStudyDirectoryTemplate() {
        return studyDirectoryTemplate.getText();
    }
    
    /**
     * 
     * @return the imageDirectoryTemplate
     */
    public String getImageDirectoryTemplate() {
        return imageDirectoryTemplate.getText();
    }
    
    /**
     * 
     * @return the segmentationFileTemplate
     */
    public String getSegmentationFileTemplate() {
        return segmentationFileTemplate.getText();
    }
    
    /**
     * 
     * @return the Scene of the view
     */
    public Scene getScene() {
        return scene;
    }
    
    /**
     * 
     * @return height of the view/scene
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * 
     * @return width of the view/scene
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Displays a popup to notify the user of an error
     * @param title, title of error
     * @param header, header of error
     * @param content, content of error
     */
    public void displayErrorAlert(String title, String header, 
    		String content) {
//    	Alert alert = new Alert(AlertType.ERROR);
//    	alert.setTitle(title);
//    	alert.setHeaderText(header);
//    	alert.setContentText(content);
//
//    	alert.showAndWait();
        System.err.println("Error Popup: " + title + "\n" + header + 
                "\n" + content);
    }
    
    /**
     * Displays a popup to notify the user of information
     * @param title, title of information
     * @param header, header of information
     * @param content, content of information
     */
    public void displayInfoAlert(String title, String header, 
    		String content) {
//    	Alert alert = new Alert(AlertType.INFORMATION);
//    	alert.setTitle(title);
//    	alert.setHeaderText(header);
//    	alert.setContentText(content);
//
//    	alert.showAndWait();
        System.err.println("Error Popup: " + title + "\n" + header + 
                "\n" + content);
    }
}
