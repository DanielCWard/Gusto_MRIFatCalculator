package Calculation;

import fatfractioncalculator.Bounds;
import fatfractioncalculator.FatVolume;
import fatfractioncalculator.Image;
import fatfractioncalculator.Mask;
import java.io.IOException;

/**
 * Need to do proper unit tests but this is just temporary.
 * @author Daniel Ward
 */
public class CalculationTest {
    public CalculationTest() {
        //
    }
    
    /**
     * Test the calculation:
     *  With these files: expected output is:
     *  
     *  TIAF (0 - 100): 0.568743804956033
     *  TIAF Volume (cm^3): 19.5702938362956
     *  TIAF Abs min: 0
     *  TIAF Mean min: 19.1191489361702
     *  TIAF Abs max: 100
     *  TIAF Mean min: 91.6617021276595
     */
//    @Test
    public void testCalculation() {
    	try {
            Image image = new Image("C:\\gusto\\TestFiles\\MRI 4.5\\GUSTO_010-04020\\MRI_RESEARCH_GUSTO_RESEARCH_20140730_171940_526000\\AX_VIBE_6ECHOES_SCAPULA_OUTPUT_FP_0058");
            Mask mask = new Mask("C:\\gusto\\TestFiles\\Segmentation files_BAT\\010-04020_BAT.nii.gz");
            Bounds bounds = new Bounds(0, 100);
            FatVolume stats = image.getMaskedVoxelStatistics(mask, bounds);
            System.err.println(stats);
            System.err.println("" + stats.getVolume(image.getVoxelVolume()));
        } catch (IOException ex) {
            System.err.println("Error!!!");
        }
    	
    }
}
