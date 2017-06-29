/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatfractioncalculator;

import java.io.IOException;
import java.util.HashMap;
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
                Mask mask = null;
                Image image = null;
                try {
                    image = new Image("C:\\gusto\\TestFiles\\MRI 4.5\\GUSTO_010-04020\\MRI_RESEARCH_GUSTO_RESEARCH_20140730_171940_526000\\AX_VIBE_6ECHOES_SCAPULA_OUTPUT_FP_0058");
//                    image = new Image("C:\\gusto\\TestFiles\\fileOptionsVersion130417\\6YR_Files/GUSTO_6YR_020-66006/GUSTO_GUSTO_RESEARCH_20170107_095236_121000/AX_VIBE_6ECHOES_BAT_LL2_SMALL_RR_FF_0103");
//                    image = new Image("C:\\gusto\\TestFiles\\MRI 4.5\\GUSTO_010-04035\\MRI_RESEARCH_GUSTO_RESEARCH_20140625_184729_416000\\AX_VIBE_6ECHOES_SCAPULA_OUTPUT_FP_0052");
//                    mask = new Mask("C:\\gusto\\TestFiles\\fileOptionsVersion130417\\NiGzFiles\\BAT6\\020-66006_BAT6.nii.gz");
                    mask = new Mask("C:\\gusto\\TestFiles\\Segmentation files_BAT\\010-04020_BAT.nii.gz");
//                    mask = new Mask(
//                            "C:\\gusto\\TestFiles\\Segmentation files_BAT\\010-04010_BAT.nii.gz");
                } catch (IOException ex) {
                    System.out.println("badF");
                }
                Bounds bounds = new Bounds(0, 1000);
//                System.err.println(image.get(70, 60, 42));
                
//                System.err.println(image.getShape());
//                for (int k = 0; k < 128; k++) {
//                    int sum = 0;
//                    for (int i = 0; i < 160; i++) {
//                        for (int j = 0; j < 136; j++) {
//                            sum += image.get(i, j, k);
//                        }
//                    }
//                    System.err.print(k + ", " + sum +"\n");
//                }
                
//                for (int i = 0; i < 160; i++) {
//                    for (int j = 0; j < 136; j++) {
//                        System.err.print(mask.get(i, j, 55) + " ");
////                        System.err.print(image.get(i, j, 55) + " ");
//                    }
//                    System.err.print("\n");
//                }
//                for (int i = 50; i < 53; i++) {
//                    for (int j = 50; j < 53; j++) {
//                        for (int k = 50; k < 53; k++) {
//                            System.err.println(image.get(k, j, i) + ": " + i +", "+j+", "+k);
//                        }
//                    }
//                }
//                for (int i = 0; i < image.getShape().getWidth(); i++) {
//                    for (int j = 0; j < image.getShape().getHeight(); j++) {
//                        for (int k = 0; k < image.getShape().getDepth(); k++) {
//                            if (image.get(i, j, k) == 505) {
//                                System.err.println("505: " + i +", "+j+", "+k);
//                            }
//                        }
//                    }
//                }
//                System.err.println(image.get(128, 79, 42));
//                System.out.println(mask.getVoxels().size()); // 7185 non zero mask pixels -> good
                System.err.println(image.getMaskedVoxelStatistics(mask, bounds));
                //Check mask
//                ArrayList<Coordinate> eggs = t.getVoxels();
//                for (Coordinate c : eggs){
//                    System.out.println(c);
//                }

                // Check MRI Data
//                for (int o = 0; o < 80; o++) {
//                    System.out.println("" + i.get(40, 40, o));
//                }
                //check MRI HEader
//                System.out.println("" + i.getVoxelVolume());

                
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
