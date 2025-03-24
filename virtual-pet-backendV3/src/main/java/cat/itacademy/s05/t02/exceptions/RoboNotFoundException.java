package cat.itacademy.s05.t02.exceptions;

public class RoboNotFoundException extends RuntimeException {
    public RoboNotFoundException(String message) {
        super(message);
    }
}
