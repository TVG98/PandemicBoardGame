package Exceptions;

public class GameLostException extends Exception {

    public GameLostException(String errorMessage) {
        super(errorMessage);
    }
}
