package fatfractioncalculator;

import fatfractioncalculator.gui.FatFractionCalculatorController;
import fatfractioncalculator.gui.FatFractionCalculatorModel;
import fatfractioncalculator.gui.FatFractionCalculatorView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
       
/**
 *
 * @author Daniel Ward
 */
public class FatFractionCalculator extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        FatFractionCalculatorModel model = new FatFractionCalculatorModel();
        FatFractionCalculatorView view = new FatFractionCalculatorView(model);
        
        new FatFractionCalculatorController(model, view);
        
        Scene scene = view.getScene();
        
        primaryStage.setTitle("MRI Fat Fraction Calculator");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(1000);
        primaryStage.setMinWidth(1500);
        primaryStage.setHeight(1000);
        primaryStage.setWidth(1500);
        
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
