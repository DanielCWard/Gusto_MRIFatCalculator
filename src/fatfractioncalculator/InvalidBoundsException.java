package fatfractioncalculator;

/**
 *
 * @author Daniel Ward
 */
/**
 * An exception that is thrown to indicate invalid bounds
 */
@SuppressWarnings("serial")
public class InvalidBoundsException extends RuntimeException {

    /**
     * Constructs a new exception with null as its detail message.
     */
    public InvalidBoundsException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * 
     * @param message
     *            the detail error message
     */
    public InvalidBoundsException(String message) {
        super(message);
    }

}
