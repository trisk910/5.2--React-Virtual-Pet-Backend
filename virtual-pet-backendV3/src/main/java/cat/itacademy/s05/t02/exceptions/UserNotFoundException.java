package cat.itacademy.s05.t02.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super("User not found with id:" + id);
    }
}
