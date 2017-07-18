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
        /**
        * Begin Test Code
        */
//        try {
//            Mask mask = new Mask("C:\\gusto\\TestFiles\\dualSegments\\10097_BAT_SNeo9M.nii.gz");
//            for (int i = 0; i <  mask.getHeight(); i++) {
//                for (int j = 0; j < mask.getWidth(); j ++) {
//                    for (int k = 0; k < mask.getDepth(); k ++) {
//                        if (mask.get(j, i, k) != 0) {
//                            System.out.println("" + mask.get(j, i, k));
//                        }
//                    }
//                }
//            }
//            
//        } catch (IOException ex) {
//            Logger.getLogger(FatFractionCalculator.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        /**
         * End Test Code
         */
            
        FatFractionCalculatorModel model = new FatFractionCalculatorModel();
        FatFractionCalculatorView view = new FatFractionCalculatorView(model);

        new FatFractionCalculatorController(model, view, primaryStage);

        Scene scene = view.getScene();

        primaryStage.setTitle("MRI Fat Fraction Calculator");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(view.getHeight());
        primaryStage.setMinWidth(view.getWidth());
        primaryStage.setHeight(view.getHeight());
        primaryStage.setWidth(view.getWidth());

        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
