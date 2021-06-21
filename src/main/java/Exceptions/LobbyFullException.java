package Exceptions;

/**
 * @author Tom van Gogh
 */
public class LobbyFullException extends Exception {
    public LobbyFullException(String errorMessage) {
        super(errorMessage);
    }
}
