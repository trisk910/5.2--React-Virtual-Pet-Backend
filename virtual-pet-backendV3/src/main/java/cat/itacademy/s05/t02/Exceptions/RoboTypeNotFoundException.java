package cat.itacademy.s05.t02.Exceptions;

public class RoboTypeNotFoundException extends RuntimeException {
    public RoboTypeNotFoundException(String message) {
        super(message);
    }
}
