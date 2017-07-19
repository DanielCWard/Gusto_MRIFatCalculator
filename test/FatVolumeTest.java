
import fatfractioncalculator.Bounds;
import fatfractioncalculator.FatVolume;
import fatfractioncalculator.Image;
import fatfractioncalculator.Mask;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Daniel Ward
 * Tests the FatVolume class.
 * Assumes that Image and Mask Classes are fully functional and tested.
 */
public class FatVolumeTest {
    
    /**
     * Original output given below. Expected outputs differ slightly as these 
     * are the new gold standard.
     * Original from Suresh Outputs:
     * % 0 - 100: 0.568743804956033
     * Volume 0 - 100: 19.5702938362956
     * @throws IOException 
     */
    @Test
    public void testFatVolumePatient04020() throws IOException {
        Image testImage = new Image("test\\testFiles\\010-04020_MRI-DIR");
        Mask testMask = new Mask("test\\testFiles\\010-04020_BAT.nii.gz");
        
        Bounds testBounds = new Bounds(0, 100);
        
        FatVolume testVolume = testImage.getMaskedVoxelStatistics(testMask, 
                testBounds, 1);
        
        Assert.assertTrue(0.5680626746506986 == testVolume.getAverageValue());
        Assert.assertTrue(19.570311333518863 == testVolume.getVolume(
                testImage.getVoxelVolume()));
        Assert.assertTrue(0 == testVolume.getAbsoluteMin());
        Assert.assertTrue(19.119148936170212 == testVolume.getMeanMin());
        Assert.assertTrue(100 == testVolume.getAbsoluteMax());
        Assert.assertTrue(91.66170212765958 == testVolume.getMeanMax());
    }
    
    /**
     * Original output given below. Expected outputs differ slightly as these 
     * are the new gold standard.
     * Original from Suresh Outputs:
     * % 0 - 100: 0.463733391228833
     * Volume 0 - 100: 18.0117015726864
     * @throws IOException 
     */
    @Test
    public void testFatVolumePatient04035() throws IOException {
        Image testImage = new Image("test\\testFiles\\010-04035_MRI-DIR");
        Mask testMask = new Mask("test\\testFiles\\010-04035_BAT.nii.gz");
        
        Bounds testBounds = new Bounds(0, 100);
        
        FatVolume testVolume = testImage.getMaskedVoxelStatistics(testMask, 
                testBounds, 1);
        
        Assert.assertTrue(0.4632305356755585 == testVolume.getAverageValue());
        Assert.assertTrue(18.011717676418254 == testVolume.getVolume(
                testImage.getVoxelVolume()));
        Assert.assertTrue(0 == testVolume.getAbsoluteMin());
        Assert.assertTrue(14.788679245283017 == testVolume.getMeanMin());
        Assert.assertTrue(100 == testVolume.getAbsoluteMax());
        Assert.assertTrue(79.2509433962264 == testVolume.getMeanMax());
        
//        System.out.println("" + testVolume.getAverageValue());
//        System.out.println("" + testVolume.getVolume(testImage.getVoxelVolume()));
//        System.out.println("" + testVolume.getAbsoluteMin());
//        System.out.println("" + testVolume.getMeanMin());
//        System.out.println("" + testVolume.getAbsoluteMax());
//        System.out.println("" + testVolume.getMeanMax());

    }
    
}
