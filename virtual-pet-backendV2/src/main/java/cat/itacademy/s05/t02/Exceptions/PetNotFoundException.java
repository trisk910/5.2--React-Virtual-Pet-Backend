package cat.itacademy.s05.t02.Exceptions;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(String message) {
        super(message);
    }
}
