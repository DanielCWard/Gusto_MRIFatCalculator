/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatfractioncalculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                ArrayList<String> s = new ArrayList<>();
                s.add("eggs");
                s.add("sausages");
                try {
                    CsvWriter csv = new CsvWriter("C:\\\\gusto\\test.csv");
//                    csv.writeRow(s);
//                    csv.close();
                    Mask mask = null;
                    mask = new Mask("C:\\gusto\\TestFiles\\fileOptionsVersion130417\\NiGzFiles\\BAT6\\020-66006_BAT6.nii.gz");
                    MaskMriMatcher matcher = new MaskMriMatcher("GUSTO_6YR_", 
                        "GUSTO_GUSTO_RESEARCH_20160403_, GUSTO_GUSTO_RESEARCH_2", 
                        "AX_VIBE_6ECHOES_SCAPULA_LL2_RR_FF_////, AX_VIBE_6ECHOES_SCAPULA_LL2_FF_////, AX_VIBE_6ECHOES_BAT_LL2_SMALL_RR_FF_////, AX_VIBE_6ECHOES_BAT2_LL2_SMALL_FF_////", 
                        "/////////_BAT6.nii.gz");
                    System.out.println(matcher.getPatientNumberFromMask(mask));
//                    System.out.println("Hello World!");
//                    Mask mask = null;
//                    Image image = null;
//                    try {
//                        image = new Image("C:\\gusto\\TestFiles\\MRI 4.5\\GUSTO_010-04020\\MRI_RESEARCH_GUSTO_RESEARCH_20140730_171940_526000\\AX_VIBE_6ECHOES_SCAPULA_OUTPUT_FP_0058");
//    //                    image = new Image("C:\\gusto\\TestFiles\\fileOptionsVersion130417\\6YR_Files/GUSTO_6YR_020-66006/GUSTO_GUSTO_RESEARCH_20170107_095236_121000/AX_VIBE_6ECHOES_BAT_LL2_SMALL_RR_FF_0103");
//    //                    image = new Image("C:\\gusto\\TestFiles\\MRI 4.5\\GUSTO_010-04035\\MRI_RESEARCH_GUSTO_RESEARCH_20140625_184729_416000\\AX_VIBE_6ECHOES_SCAPULA_OUTPUT_FP_0052");
//    //                    mask = new Mask("C:\\gusto\\TestFiles\\fileOptionsVersion130417\\NiGzFiles\\BAT6\\020-66006_BAT6.nii.gz");
//                        mask = new Mask("C:\\gusto\\TestFiles\\Segmentation files_BAT\\010-04020_BAT.nii.gz");
//    //                    mask = new Mask(
//    //                            "C:\\gusto\\TestFiles\\Segmentation files_BAT\\010-04010_BAT.nii.gz");
//                    } catch (IOException ex) {
//                        System.out.println("badF");
//                    }
//                    Bounds bounds = new Bounds(0, 1000);
//                    //Print eader of mask
//                    Map<String, String> head = mask.getHeader();
//                    for (String st : head.keySet()) {
//                        System.err.println(st + ", " + head.get(st));
//                    }
//                FatVolume stats = image.getMaskedVoxelStatistics(mask, bounds);
//                System.err.println(stats);
//                System.err.println("" + stats.getVolume(image.getVoxelVolume()));
                } catch (IOException ex) {
                    Logger.getLogger(FatFractionCalculator.class.getName()).log(Level.SEVERE, null, ex);
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
