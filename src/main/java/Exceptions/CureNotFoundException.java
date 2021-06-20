package Exceptions;

/**
 * @author : Daniel Paans
 */

public class CureNotFoundException extends Exception {
    public CureNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
