import fatfractioncalculator.Image;
import fatfractioncalculator.Shape;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Daniel Ward
 * Test of the Image class
 */
public class ImageTest {
    @Test
    public void testImage() throws IOException {
        Image testImage = new Image("test\\testFiles\\010-04020_MRI-DIR");
        
        Assert.assertEquals("3 ", testImage.getMRIStrength());
        Assert.assertEquals("004Y", testImage.getPatientAge());
        Assert.assertEquals("20100606", testImage.getPatientBirthday());
        Assert.assertEquals("1.04", testImage.getPatientHeight());
        Assert.assertEquals("010-04020 ", testImage.getPatientID());
        Assert.assertEquals("M ", testImage.getPatientSex());
        Assert.assertEquals("16", testImage.getPatientWeight());
        Assert.assertEquals("20140730", testImage.getStudyDate());
        Assert.assertEquals(
                "1.3.12.2.1107.5.2.19.45224.30000014073000251495600000022", 
                testImage.getStudyUID());
        Assert.assertEquals("MRI Image\n" +
                            "Loaded from: test\\testFiles\\010-04020_MRI-DIR\n" 
                            + "Dimensions: (160.0, 160.0, 104.0)\n" +
                            "Patient ID: 010-04020 \n" +
                            "Study Date: 20140730\n" +
                            "", testImage.toString());
        
        Shape expectedShape = new Shape(160, 160, 104);
        Assert.assertTrue(expectedShape.equals(testImage.getShape()));
        
        Shape expectedVoxelVolume = new Shape(1.5625, 1.5625, 1.5999999046326);
        Assert.assertTrue(expectedVoxelVolume.equals(
                testImage.getVoxelDimensions()));
        
        Assert.assertTrue(3.906249767169433 == testImage.getVoxelVolume());
        
        
        // 5 random data points
        Assert.assertEquals(928, testImage.get(1, 47, 52));
        Assert.assertEquals(131, testImage.get(59, 41, 19));
        Assert.assertEquals(33, testImage.get(69, 58, 40));
        Assert.assertEquals(1000, testImage.get(77, 26, 27));
        Assert.assertEquals(305, testImage.get(85, 104, 22));
    }
    
    
    
}
