package Exceptions;

/**
 * @author : Daniel Paans
 */

public class CityNotFoundException extends Exception {

    public CityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
