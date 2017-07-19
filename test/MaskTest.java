import fatfractioncalculator.Mask;
import fatfractioncalculator.Shape;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Daniel Ward
 * Test of the Mask Class
 */
public class MaskTest {
    
    @Test
    public void testMask() throws IOException {
        Mask testMask = new Mask("test\\testFiles\\010-04020_BAT.nii.gz");
        
        Assert.assertEquals("test\\testFiles\\010-04020_BAT.nii.gz", 
                testMask.getPath());
        Assert.assertEquals("010-04020_BAT.nii.gz", 
                testMask.getFilename());
        
        Shape expectedShape = new Shape(160, 160, 104);
        Assert.assertTrue(expectedShape.equals(testMask.getShape()));
        
        Assert.assertTrue(testMask.checkShape(expectedShape));
        
        Shape badShape = new Shape(130, 160, 104);
        Assert.assertFalse(testMask.checkShape(badShape));
        
        // 5 Random Data Points
        Assert.assertEquals(0, testMask.get(0, 0, 0));
        Assert.assertEquals(0, testMask.get(100, 35, 55));
        Assert.assertEquals(1, testMask.get(114, 66, 55));
        Assert.assertEquals(1, testMask.get(109, 68, 70));
        Assert.assertEquals(1, testMask.get(125, 76, 38));
        
    }
    
    @Test
    public void testTwoSegmentMask() throws IOException {
        Mask testMask = new Mask("test\\testFiles\\10097_BAT_SNeo9M.nii.gz");
        
        Assert.assertEquals("test\\testFiles\\10097_BAT_SNeo9M.nii.gz", 
                testMask.getPath());
        Assert.assertEquals("10097_BAT_SNeo9M.nii.gz", 
                testMask.getFilename());
        
        Shape expectedShape = new Shape(160, 144, 112);
        Assert.assertTrue(expectedShape.equals(testMask.getShape()));
        
        Assert.assertTrue(testMask.checkShape(expectedShape));
        
        Shape badShape = new Shape(130, 160, 104);
        Assert.assertFalse(testMask.checkShape(badShape));
        
        // 7 Random Data Points
        Assert.assertEquals(0, testMask.get(0, 0, 0));
        Assert.assertEquals(0, testMask.get(100, 35, 55));
        Assert.assertEquals(1, testMask.get(36, 77, 43));
        Assert.assertEquals(1, testMask.get(52, 54, 54));
        Assert.assertEquals(1, testMask.get(56, 73, 77));
        Assert.assertEquals(2, testMask.get(92, 100, 55));
        Assert.assertEquals(2, testMask.get(89, 98, 67));
    }
    
}
