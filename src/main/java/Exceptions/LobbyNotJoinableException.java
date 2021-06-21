package Exceptions;

/**
 * @author Tom van Gogh
 */
public class LobbyNotJoinableException extends Exception {

    public LobbyNotJoinableException(String errorMessage) {
        super(errorMessage);
    }
}
