/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatfractioncalculator;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
       
/**
 *
 * @author Dan
 */
public class FatFractionCalculator extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
                FatMask t = null;
                Image i = null;
                try {
                    i = new Image("C:\\gusto\\TestFiles\\MRI 4.5\\GUSTO_010-04035\\MRI_RESEARCH_GUSTO_RESEARCH_20140625_184729_416000\\AX_VIBE_6ECHOES_SCAPULA_OUTPUT_FP_0052");
                    t = new FatMask(
                            "C:\\gusto\\TestFiles\\Segmentation files_BAT\\010-04010_BAT.nii.gz");
                } catch (IOException ex) {
                    System.out.println("badF");
                }
//                ArrayList<Coordinate> eggs = t.getMaskedVoxels();
//                for (Coordinate c : eggs){
//                    System.out.println(c);
//                }
                for (int o = 0; o < 80; o++) {
                    System.out.println("" + i.get(40, 40, o));
                }
                
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
