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
    private final int height = 1000;
    private final int width = 2000;
    
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
     * Populates the grid with the BAT, WAT and TIAF sliders
     */
    private void populateGridWithSliders() {
        HBox batSliders = new HBox();
        BATMinSlider = new Slider();
        BATMaxSlider = new Slider();
        // Pack the two sliders into an HBox side by side
        batSliders.getChildren().addAll(createThresholdSlider(BATMinSlider, 
                    BATMinSliderLabel), 
                createThresholdSlider(BATMaxSlider, BATMaxSliderLabel));
        HBox.setHgrow(batSliders, Priority.ALWAYS);
        // Put HBox in the grid across all 2 rows
        grid.add(batSliders, 0, 1, 2, 1);
        
        HBox watSliders = new HBox();
        WATMinSlider = new Slider();
        WATMaxSlider = new Slider();
        // Pack the two sliders into an HBox side by side
        watSliders.getChildren().addAll(createThresholdSlider(WATMinSlider, 
                    WATMinSliderLabel), 
                createThresholdSlider(WATMaxSlider, WATMaxSliderLabel));
        HBox.setHgrow(watSliders, Priority.ALWAYS);
        // Put HBox in the grid across all 2 rows
        grid.add(watSliders, 0, 2, 2, 1);
        
        HBox TIAFSliders = new HBox();
        TIAFMinSlider = new Slider();
        TIAFMaxSlider = new Slider();
        // Pack the two sliders into an HBox side by side
        TIAFSliders.getChildren().addAll(createThresholdSlider(TIAFMinSlider, 
                    TIAFMinSliderLabel), 
                createThresholdSlider(TIAFMaxSlider, TIAFMaxSliderLabel));
        HBox.setHgrow(TIAFSliders, Priority.ALWAYS);
        // Put HBox in the grid across all 2 rows
        grid.add(TIAFSliders, 0, 3, 2, 1);
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
        
        // Add it all to row 4 of the grid spanning all 2 columns and 1 row
        grid.add(hBox, 0, 4, 2, 1);
        
        // Select CSV Button
        setCsvFileButton = createButton(
                setCsvFileButtonLabel);
        
        // Start Calculation Button
        startCalculationButton = createButton(
                startCalculationButtonLabel);
        
        vBox.getChildren().addAll(setCsvFileButton, 
                startCalculationButton);
        
        // Add it all to row 6 of the grid spanning all 2 columns and 1 row
        grid.add(vBox, 0, 6, 2, 1);
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
        
        // Add it all to row 7 of the grid spanning all 2 columns and 1 row
        grid.add(vBox, 0, 7, 2, 1);
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
        
        // Add to grid at column 0 row 5
        grid.add(vBox, 0, 5, 2, 1);
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
     * Sets the value of the BAT min threshold
     * @param value the value to set the slider to
     */
    public void setBATMinSliderValue(double value) {
        BATMinSlider.setValue(value);
    }
    
    /**
     * Sets the value of the BAT max threshold
     * @param value the value to set the slider to
     */
    public void setBATMaxSliderValue(double value) {
        BATMaxSlider.setValue(value);
    }
    
    /**
     * Sets the value of the WAT min threshold
     * @param value the value to set the slider to
     */
    public void setWATMinSliderValue(double value) {
        WATMinSlider.setValue(value);
    }
    
    /**
     * Sets the value of the WAT max threshold
     * @param value the value to set the slider to
     */
    public void setWATMaxSliderValue(double value) {
        WATMaxSlider.setValue(value);
    }
    
    /**
     * Sets the value of the TIAF min threshold
     * @param value the value to set the slider to
     */
    public void setTIAFMinSliderValue(double value) {
        TIAFMinSlider.setValue(value);
    }
    
    /**
     * Sets the value of the TIAF max threshold
     * @param value the value to set the slider to
     */
    public void setTIAFMaxSliderValue(double value) {
        TIAFMaxSlider.setValue(value);
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
     * @return the value of the BAT min threshold
     */
    public int getBATMinSliderValue() {
        return (int) BATMinSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the BAT max threshold
     */
    public int getBATMaxSliderValue() {
        return (int) BATMaxSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the WAT min threshold
     */
    public int getWATMinSliderValue() {
        return (int) WATMinSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the WAT max threshold
     */
    public int getWATMaxSliderValue() {
        return (int) WATMaxSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the TIAF min threshold
     */
    public int getTIAFMinSliderValue() {
        return (int) TIAFMinSlider.getValue();
    }
    
    /**
     * 
     * @return the value of the TIAF max threshold
     */
    public int getTIAFMaxSliderValue() {
        return (int) TIAFMaxSlider.getValue();
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
