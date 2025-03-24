package cat.itacademy.s05.t02.exceptions;

public class RoboTypeNotFoundException extends RuntimeException {
    public RoboTypeNotFoundException(String message) {
        super(message);
    }
}
