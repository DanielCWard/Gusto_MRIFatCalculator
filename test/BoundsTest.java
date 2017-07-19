
import fatfractioncalculator.Bounds;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the Bounds Class
 * @author Daniel Ward
 */
public class BoundsTest {
    
    @Test
    public void testBounds() {
        Bounds boundsTest = new Bounds(34, 93);
        
        Assert.assertEquals(34, boundsTest.getLower());
        
        Assert.assertEquals(93, boundsTest.getUpper());
        
        Assert.assertTrue(boundsTest.inBoundsScaledTen(437));
        Assert.assertFalse(boundsTest.inBoundsScaledTen(1000));
        
        Assert.assertTrue(boundsTest.inBounds(43));
        Assert.assertFalse(boundsTest.inBounds(100));
    }
    
}
