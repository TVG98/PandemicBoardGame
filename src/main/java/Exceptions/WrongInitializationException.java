package Exceptions;

/**
 * @author : Thimo van Velzen
 */
public class WrongInitializationException extends Exception {

    public WrongInitializationException(String errorMessage) {
        super(errorMessage);
    }
}
