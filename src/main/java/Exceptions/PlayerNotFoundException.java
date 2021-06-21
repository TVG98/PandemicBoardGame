package Exceptions;

/**
 * @author Thimo van Velzen
 */

public class PlayerNotFoundException extends Exception{
    public PlayerNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
