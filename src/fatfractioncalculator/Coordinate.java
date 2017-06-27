/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatfractioncalculator;

/**
 * Coordinate class for 3d Coordinates: x, y and z
 * @author Daniel Ward
 */
public class Coordinate {
    /* Class variables*/
    private final int x;
    private final int y;
    private final int z;
    
    /**
     * Init the variables to final values
     * @param x : x coordinate
     * @param y : y coordinate
     * @param z : z coordinate
     */
    public Coordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * @return x value of coordinate
     */
    public int getX(){
        return x;
    }
    
    /**
     * @return y value of coordinate
     */
    public int getY(){
        return y;
    }
    
    /**
     * @return z value of coordinate
     */
    public int getZ(){
        return z;
    }
    
    /**
     * @return String representation of coordinate
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
    
    /**
     * 
     * @param object Object to compare to
     * @return true iff object is equal to this instance
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Coordinate)) {
            return false;
        } else {
            Coordinate compare = (Coordinate) object;
            return compare.getX() == x && 
                    compare.getY() == y && 
                    compare.getZ() == z;
        }
    }
    
    /**
     * Get the hashcode of the coordinate
     * @return hashcode of the coordinate
     */
    @Override
    public int hashCode(){
        final int prime = 31; // an odd base prime
        int result = prime * x;
        result += prime * y;
        result += prime * z;
        
        return result;
    }
    
    
}
