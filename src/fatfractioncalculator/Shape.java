/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatfractioncalculator;

/**
 *
 * @author Daniel Ward
 */
public class Shape {
    
    /* Class variables*/
    private final double x; // x dimension
    private final double y; // y dimension
    private final double z; // z direction
    
    /**
     * Initalise Class variables
     * @param x x dimension
     * @param y y dimension
     * @param z z dimension
     */
    public Shape(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * 
     * @return x dimension (width)
     */
    public double getX() {
        return x;
    }
    
    /**
     * 
     * @return y dimension (height)
     */
    public double getY() {
        return y;
    }
    
    /**
     * 
     * @return z dimension (depth)
     */
    public double getZ() {
        return z;
    }
    
    /**
     * 
     * @return x dimension (width)
     */
    public double getWidth() {
        return getX();
    }
    
    /**
     * 
     * @return y dimension (height)
     */
    public double getHeight() {
        return getY();
    }
    
    /**
     * 
     * @return z dimension (depth)
     */
    public double getDepth() {
        return getZ();
    }
    
    /**
     * 
     * @return volume of the shape
     */
    public double getVolume() {
        return x * y * z;
    }
    
    /**
     * Get the hashcode of the shape
     * @return hashcode of the shape
     */
    @Override
    public int hashCode() {
        final int prime = 31; // an odd base prime
        double result = prime * x;
        result += prime * y;
        result += prime * z;
        
        return (int)result;
    }

    /**
     * 
     * @param object object to compare to
     * @return true iff object == this instance
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Coordinate)) {
            return false;
        } else {
            Shape compare = (Shape) object;
            return compare.getX() == x && 
                    compare.getY() == y && 
                    compare.getZ() == z;
        }
    }
    
    /**
     * @return String representation of Shape
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
    
    
}
