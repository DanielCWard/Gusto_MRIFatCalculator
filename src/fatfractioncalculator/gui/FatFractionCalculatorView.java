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
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
    
    // Fonts
    private final Font titleFont = Font.font("SanSerif", FontWeight.BOLD, 25);
    private final Font labelFont = Font.font("SanSerif", 18);
    
    // Dimensions
    private final int height = 650;
    private final int width = 1000;
    
    public FatFractionCalculatorView(FatFractionCalculatorModel model) {
        this.model = model;
        
        // Initalise the view grid to pack everything into        
        grid = new GridPane();
        grid.setPrefSize(height, width);
        grid.setMinSize(height, width);        
        
        VBox box = getThresholdSlider(BATMinSlider, BATMinSliderLabel);//TIAFMaxSliderLabel);
        
        grid.add(box, 1, 1, 1, 1);
        
        // Set the scene
        scene = new Scene(grid, height, width);
    }
    
    private VBox getThresholdSlider(Slider slider, String label) {
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
     * 
     * @return the Scene of the view
     */
    public Scene getScene() {
        return scene;
    }
    
    
    
    
}
