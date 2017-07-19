import fatfractioncalculator.Shape;
import org.junit.Assert;
import org.junit.Test;


/**
 * Tests the Shape class
 * @author Daniel Ward
 */
public class ShapeTest {
    
    @Test
    public void testShape() {
        Shape shapeTest = new Shape(10, 55.3, 34.6);
        
        Assert.assertTrue(10 == shapeTest.getX());
        Assert.assertTrue(10 == shapeTest.getWidth());
        
        Assert.assertTrue(55.3 == shapeTest.getY());
        Assert.assertTrue(55.3 == shapeTest.getHeight());
        
        Assert.assertTrue(34.6 == shapeTest.getZ());
        Assert.assertTrue(34.6 == shapeTest.getDepth());
        
        Assert.assertTrue((10 * 55.3 * 34.6) == shapeTest.getVolume());
        
        Shape expectedShape = new Shape(10, 55.3, 34.6);
        Assert.assertTrue(shapeTest.equals(expectedShape));
        
        Shape unexpectedShape = new Shape(10, 55.2, 34.6);
        Assert.assertFalse(shapeTest.equals(unexpectedShape));
        
        Assert.assertEquals("(10.0, 55.3, 34.6)", shapeTest.toString());
    }
}
