package Exceptions;

/**
 * @author : Thimo van Velzen
 */

public class VirusNotFoundException extends Exception {

    public VirusNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
