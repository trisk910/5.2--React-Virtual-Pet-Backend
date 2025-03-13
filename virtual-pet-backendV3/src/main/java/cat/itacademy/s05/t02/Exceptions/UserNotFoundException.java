package cat.itacademy.s05.t02.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int id) {
        super("User not found with id:" + id);
    }
}
