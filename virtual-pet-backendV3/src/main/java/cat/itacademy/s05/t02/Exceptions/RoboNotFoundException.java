package cat.itacademy.s05.t02.Exceptions;

public class RoboNotFoundException extends RuntimeException {
    public RoboNotFoundException(String message) {
        super(message);
    }
}
