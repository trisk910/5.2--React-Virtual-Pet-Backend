package cat.itacademy.s05.t02.exceptions;

public class FightNotFoundException extends RuntimeException {
    public FightNotFoundException(String message) {
        super(message);
    }
}
