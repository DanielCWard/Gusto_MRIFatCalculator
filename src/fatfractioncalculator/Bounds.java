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
     * @throws InvalidBoundsException if !(upper > lower)
     */
    public Bounds(int lower, int upper) throws InvalidBoundsException {
        if (!(upper > lower)) {
            throw new InvalidBoundsException(
                    "Upper limit is not larger than lower limit!");
        }
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
    
    /**
     * 
     * @param value value to compare
     * @return true iff value is greater or equal to lower bound and less than
     *         or equal to upper bound.
     */
    public boolean inBounds(int value) {
        return (value >= lower && value <= upper);
    }
    
    /**
     * 
     * @return a copy of the bounds instance
     */
    public Bounds copy() {
        return new Bounds(getLower(), getUpper());
    }
}
