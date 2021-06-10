package Exceptions;

public class PlayerNotFoundException extends Exception{
    public PlayerNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
