package Exceptions;

/**
 * @author : Thimo van Velzen
 */

public class CityNotFoundException extends Exception {

    public CityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
