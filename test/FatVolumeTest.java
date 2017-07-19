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
 * These Tests implicitly tests the getMaskedVoxels function of the Mask class
 */
public class FatVolumeTest {
    
    /**
     * Original output given below. Expected outputs differ slightly as these 
     * are the new gold standard.
     * Original from Suresh Outputs:
     * % 0 - 100: 0.568743804956033
     * Volume 0 - 100: 19.5702938362956
     * % 20 - 60: 0.422968210089842
     * Volume 20 - 60: 11.3046767190098
     * % 80 - 90: 0.845497674418605
     * Volume 80 - 90: 2.51952884718775
     * @throws IOException 
     */
    @Test
    public void testFatVolumePatient04020() throws IOException {
        Image testImage = new Image("test\\testFiles\\010-04020_MRI-DIR");
        Mask testMask = new Mask("test\\testFiles\\010-04020_BAT.nii.gz");
        
        // Test TS range
        
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
        
        // Test BAT range
        
        testBounds = new Bounds(20, 60);
        
        testVolume = testImage.getMaskedVoxelStatistics(testMask, 
                testBounds, 1);
        
        Assert.assertTrue(0.42296821008984103 == testVolume.getAverageValue());
        Assert.assertTrue(11.30468682618834 == testVolume.getVolume(
                testImage.getVoxelVolume()));
        Assert.assertTrue(20 == testVolume.getAbsoluteMin());
        Assert.assertTrue(25.763829787234044 == testVolume.getMeanMin());
        Assert.assertTrue(60 == testVolume.getAbsoluteMax());
        Assert.assertTrue(57.6063829787234 == testVolume.getMeanMax());
        
        // Test WAT range
        
        testBounds = new Bounds(80, 90);
        
        testVolume = testImage.getMaskedVoxelStatistics(testMask, 
                testBounds, 1);
        
        Assert.assertTrue(0.8451476923076923 == testVolume.getAverageValue());
        Assert.assertTrue(2.539062348660132 == testVolume.getVolume(
                testImage.getVoxelVolume()));
        Assert.assertTrue(80 == testVolume.getAbsoluteMin());
        Assert.assertTrue(80.87142857142857 == testVolume.getMeanMin());
        Assert.assertTrue(90 == testVolume.getAbsoluteMax());
        Assert.assertTrue(88.84761904761905 == testVolume.getMeanMax());
    }
    
    /**
     * Original output given below. Expected outputs differ slightly as these 
     * are the new gold standard.
     * Original from Suresh Outputs:
     * % 0 - 100: 0.463733391228833
     * Volume 0 - 100: 18.0117015726864
     * % 20 - 60: 0.407627319171821
     * Volume 20 - 60: 14.5273298956453
     * % 80 - 90: 0.846729357798165
     * Volume 80 - 90: 0.851561687886714
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
        
        // Test BAT range
        
        testBounds = new Bounds(20, 60);
        
        testVolume = testImage.getMaskedVoxelStatistics(testMask, 
                testBounds, 1);
        
        Assert.assertTrue(0.40734854994629427 == testVolume.getAverageValue());
        Assert.assertTrue(14.546874132938969 == testVolume.getVolume(
                testImage.getVoxelVolume()));
        Assert.assertTrue(20 == testVolume.getAbsoluteMin());
        Assert.assertTrue(23.856603773584904 == testVolume.getMeanMin());
        Assert.assertTrue(60 == testVolume.getAbsoluteMax());
        Assert.assertTrue(56.79433962264151 == testVolume.getMeanMax());
        
        // Test WAT range
        
        testBounds = new Bounds(80, 90);
        
        testVolume = testImage.getMaskedVoxelStatistics(testMask, 
                testBounds, 1);
        
        Assert.assertTrue(0.846729357798165 == testVolume.getAverageValue());
        Assert.assertTrue(0.8515624492429364 == testVolume.getVolume(
                testImage.getVoxelVolume()));
        Assert.assertTrue(80 == testVolume.getAbsoluteMin());
        Assert.assertTrue(81.27083333333334 == testVolume.getMeanMin());
        Assert.assertTrue(90 == testVolume.getAbsoluteMax());
        Assert.assertTrue(88.4125 == testVolume.getMeanMax());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testFatVolumeTwoSegmentMaskFile() throws IOException {
        Image testImage = new Image("test\\testFiles\\10097_MRI-DIR");
        Mask testMask = new Mask("test\\testFiles\\10097_BAT_SNeo9M.nii.gz");
        
        Bounds testBounds = new Bounds(0, 100);
        
        // Test first segment, mask Value 1
        FatVolume testVolume = testImage.getMaskedVoxelStatistics(testMask, 
                testBounds, 1);
        
        Assert.assertTrue(0.4678743062992893 == testVolume.getAverageValue());
        Assert.assertTrue(29.55953496503574 == testVolume.getVolume(
                testImage.getVoxelVolume()));
        Assert.assertTrue(0 == testVolume.getAbsoluteMin());
        Assert.assertTrue(1.6949152542372883 == testVolume.getMeanMin());
        Assert.assertTrue(100 == testVolume.getAbsoluteMax());
        Assert.assertTrue(98.79152542372881 == testVolume.getMeanMax());
        
        // Test BAT range
        
        testBounds = new Bounds(20, 60);
        
        testVolume = testImage.getMaskedVoxelStatistics(testMask, 
                testBounds, 1);
        
        Assert.assertTrue(0.3611381537492124 == testVolume.getAverageValue());
        Assert.assertTrue(9.13464745195438 == testVolume.getVolume(
                testImage.getVoxelVolume()));
        Assert.assertTrue(20 == testVolume.getAbsoluteMin());
        Assert.assertTrue(21.563793103448276 == testVolume.getMeanMin());
        Assert.assertTrue(60 == testVolume.getAbsoluteMax());
        Assert.assertTrue(56.324137931034485 == testVolume.getMeanMax());
        
        // Test WAT range
        
        testBounds = new Bounds(80, 90);
        
        testVolume = testImage.getMaskedVoxelStatistics(testMask, 
                testBounds, 1);
        
        Assert.assertTrue(0.8523850187265918 == testVolume.getAverageValue());
        Assert.assertTrue(3.8420776144798676 == testVolume.getVolume(
                testImage.getVoxelVolume()));
        Assert.assertTrue(80 == testVolume.getAbsoluteMin());
        Assert.assertTrue(80.65172413793104 == testVolume.getMeanMin());
        Assert.assertTrue(90 == testVolume.getAbsoluteMax());
        Assert.assertTrue(89.21896551724139 == testVolume.getMeanMax());
        
        // Test second segment, mask Value 2
        
        testBounds = new Bounds(0, 100);
        
        testVolume = testImage.getMaskedVoxelStatistics(testMask, 
                testBounds, 2);
        
        Assert.assertTrue(0.32148755787037037 == testVolume.getAverageValue());
        Assert.assertTrue(4.973116193124502 == testVolume.getVolume(
                testImage.getVoxelVolume()));
        Assert.assertTrue(0 == testVolume.getAbsoluteMin());
        Assert.assertTrue(14.390625 == testVolume.getMeanMin());
        Assert.assertTrue(100 == testVolume.getAbsoluteMax());
        Assert.assertTrue(71.85625 == testVolume.getMeanMax());
        
        // Test BAT range
        
        testBounds = new Bounds(20, 60);
        
        testVolume = testImage.getMaskedVoxelStatistics(testMask, 
                testBounds, 2);
        
        Assert.assertTrue(0.3427173678532902 == testVolume.getAverageValue());
        Assert.assertTrue(1.333934812218291 == testVolume.getVolume(
                testImage.getVoxelVolume()));
        Assert.assertTrue(20 == testVolume.getAbsoluteMin());
        Assert.assertTrue(20.792307692307695 == testVolume.getMeanMin());
        Assert.assertTrue(59 == testVolume.getAbsoluteMax());
        Assert.assertTrue(49.18076923076923 == testVolume.getMeanMax());
        
        // Test WAT range
        
        testBounds = new Bounds(80, 90);
        
        testVolume = testImage.getMaskedVoxelStatistics(testMask, 
                testBounds, 2);
        
        Assert.assertTrue(0.859794964028777 == testVolume.getAverageValue());
        Assert.assertTrue(0.4000365456274918 == testVolume.getVolume(
                testImage.getVoxelVolume()));
        Assert.assertTrue(80 == testVolume.getAbsoluteMin());
        Assert.assertTrue(81.65384615384616 == testVolume.getMeanMin());
        Assert.assertTrue(90 == testVolume.getAbsoluteMax());
        Assert.assertTrue(89.2 == testVolume.getMeanMax());
    }
    
}
