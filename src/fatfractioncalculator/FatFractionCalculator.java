package fatfractioncalculator;

import fatfractioncalculator.gui.FatFractionCalculatorController;
import fatfractioncalculator.gui.FatFractionCalculatorModel;
import fatfractioncalculator.gui.FatFractionCalculatorView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
//        try {
//            Image image = new Image("C:\\gusto\\TestFiles\\MRI 4.5\\GUSTO_010-04020\\MRI_RESEARCH_GUSTO_RESEARCH_20140730_171940_526000\\AX_VIBE_6ECHOES_SCAPULA_OUTPUT_FP_0058");
//            Mask mask = new Mask("C:\\gusto\\TestFiles\\Segmentation files_BAT\\010-04020_BAT.nii.gz");
//            Bounds bounds = new Bounds(0, 100);
//            FatVolume stats = image.getMaskedVoxelStatistics(mask, bounds);
//            System.err.println(stats);
//            System.err.println("" + stats.getVolume(image.getVoxelVolume()));
//        } catch (IOException ex) {
//            System.err.println("Error!!!");
//        }
        
        FatFractionCalculatorModel model = new FatFractionCalculatorModel();
        FatFractionCalculatorView view = new FatFractionCalculatorView(model);
        
        new FatFractionCalculatorController(model, view, primaryStage);
        
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
