package fatfractioncalculator;

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
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World");
//            }
//        });
//        
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
        
//        Scene scene = new Scene(root, 300, 250);
        FatFractionCalculatorModel model = new FatFractionCalculatorModel();
        FatFractionCalculatorView view = new FatFractionCalculatorView(model);
        
        Scene scene = view.getScene();
        
        primaryStage.setTitle("MRI Fat Fraction Calculator");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(750);
        primaryStage.setMinWidth(1000);
        primaryStage.setHeight(750);
        primaryStage.setWidth(1000);
        
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
