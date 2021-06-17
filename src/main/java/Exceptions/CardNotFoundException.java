package Exceptions;

public class CardNotFoundException extends Exception {
    public CardNotFoundException(String ErrorMessage) {
        super(ErrorMessage);
    }
}
