package Exceptions;

/**
 * @author : Thimo van Velzen
 */

public class CureNotFoundException extends Exception {
    public CureNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
