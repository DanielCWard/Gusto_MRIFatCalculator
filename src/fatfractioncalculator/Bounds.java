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
public class Bounds {
    
    /* Class variables*/
    private final int lower;
    private final int upper;
    
    /**
     * Set the class variables/boundary values
     * @param lower lower bound
     * @param upper upper bound
     */
    public Bounds(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
    }
    
    /**
     * 
     * @return the upper bound
     */
    public int getUpper() {
        return upper;
    }
    
    /**
     * 
     * @return the lower bound
     */
    public int getLower() {
        return lower;
    }
    
    
}