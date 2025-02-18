package cat.itacademy.s05.t02.S05T02.Exceptions;

public class DeckEmptyException extends RuntimeException {
    public DeckEmptyException(String message) {
        super("No cards left in the deck.");
    }
}
