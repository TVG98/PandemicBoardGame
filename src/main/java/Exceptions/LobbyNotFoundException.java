package Exceptions;

/**
 * @author Tom van Gogh
 */
public class LobbyNotFoundException extends Exception {

    public LobbyNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
