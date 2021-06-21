package Exceptions;

/**
 * @author Daniel Paans
 */
public class CardNotFoundException extends Exception {
    public CardNotFoundException(String ErrorMessage) {
        super(ErrorMessage);
    }
}
