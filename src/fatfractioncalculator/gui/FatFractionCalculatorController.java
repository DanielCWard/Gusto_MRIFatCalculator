package fatfractioncalculator.gui;

import fatfractioncalculator.Bounds;

/**
 *
 * @author Daniel Ward
 */
public class FatFractionCalculatorController {
    
    /* Class Variables */
    FatFractionCalculatorModel model; // The model
    FatFractionCalculatorView view; // The view
    
    public FatFractionCalculatorController(FatFractionCalculatorModel model, 
            FatFractionCalculatorView view) {
        this.model = model;
        this.view = view;
        
        System.out.print("" + model.getSubjectDirectoryTemplate());
        
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
    }
}
