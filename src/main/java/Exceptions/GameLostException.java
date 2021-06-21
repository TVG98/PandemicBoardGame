package Exceptions;

/**
 * @author Thimo van Velzen
 */

public class GameLostException extends Exception {

    public GameLostException(String errorMessage) {
        super(errorMessage);
    }
}
