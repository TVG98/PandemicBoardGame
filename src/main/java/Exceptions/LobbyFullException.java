package Exceptions;

public class LobbyFullException extends Exception {
    public LobbyFullException(String errorMessage) {
        super(errorMessage);
    }
}
