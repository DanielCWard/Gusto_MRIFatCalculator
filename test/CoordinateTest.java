import fatfractioncalculator.Coordinate;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the Coordinate class
 * @author Daniel Ward
 */
public class CoordinateTest {
    
    @Test
    public void testCoordinate() {
        Coordinate coordinateTest = new Coordinate(10, 55, 34);
        
        Assert.assertTrue(10 == coordinateTest.getX());
        
        Assert.assertTrue(55 == coordinateTest.getY());
        
        Assert.assertTrue(34 == coordinateTest.getZ());
        
        Coordinate expectedCoordinate = new Coordinate(10, 55, 34);
        Assert.assertTrue(coordinateTest.equals(expectedCoordinate));
        
        Coordinate unexpectedCoordinate = new Coordinate(10, 55, 39);
        Assert.assertFalse(coordinateTest.equals(unexpectedCoordinate));
        
        Assert.assertEquals("(10, 55, 34)", coordinateTest.toString());
    }
}
